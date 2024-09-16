package com.jpnacaratti.modtruck.bluetooth

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothProfile
import android.content.Context
import android.util.Log
import kotlinx.coroutines.*
import java.util.UUID
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanFilter
import android.bluetooth.le.ScanSettings
import android.bluetooth.*
import android.bluetooth.le.*
import android.os.Handler
import android.os.Looper
import com.jpnacaratti.modtruck.utils.MessageBuilder
import com.jpnacaratti.modtruck.utils.Utilities

@SuppressLint("MissingPermission")
class BluetoothService(private val context: Context) {

    private val bluetoothAdapter: BluetoothAdapter? = BluetoothAdapter.getDefaultAdapter()
    private val job = SupervisorJob()
    private val scope = CoroutineScope(Dispatchers.IO + job)
    private val handler = Handler(Looper.getMainLooper())

    private val ARDUINO_BLUETOOTH_NAME = "Scania-ModTruck"

    private val SERVICE_UUID: UUID = UUID.fromString("12345678-1234-5678-1234-56789abcdef0") // Arduino service UUID

    // Arduino characteristics UUIDs
    private val TRUCK_INFO_UUID: UUID = UUID.fromString("abcdef01-1234-5678-1234-56789abcdef1")
    private val SMARTBOX_INFO_UUID: UUID = UUID.fromString("abcdef02-1234-5678-1234-56789abcdef2")

    private val DESCRIPTOR_UUID: UUID = UUID.fromString("00002902-0000-1000-8000-00805f9b34fb") // Scania ModTruck UUID

    private var bluetoothGatt: BluetoothGatt? = null

    private val truckInfoMessageBuilder: MessageBuilder = MessageBuilder(3)
    private val smartBoxMessageBuilder: MessageBuilder = MessageBuilder(3)

    private val leScanCallback = object : ScanCallback() {
        override fun onScanResult(callbackType: Int, result: ScanResult) {
            super.onScanResult(callbackType, result)
            val device = result.device
            if (device.name == null) return
            Log.d("BluetoothService", "Device found: ${device.name}")

            bluetoothAdapter?.bluetoothLeScanner?.stopScan(this)

            scope.launch {
                connectToDevice(device)
            }
        }

        override fun onScanFailed(errorCode: Int) {
            super.onScanFailed(errorCode)
            Log.e("BluetoothService", "Scan failed: $errorCode")
        }
    }

    init {
        startScan()
    }

    private fun startScan() {
        val bluetoothLeScanner = bluetoothAdapter?.bluetoothLeScanner
        val scanFilter = ScanFilter.Builder()
            .setDeviceName(ARDUINO_BLUETOOTH_NAME)
            .build()

        val scanFilters = listOf(scanFilter)
        val scanSettings = ScanSettings.Builder()
            .setScanMode(ScanSettings.SCAN_MODE_LOW_POWER)
            .build()

        bluetoothLeScanner?.startScan(scanFilters, scanSettings, leScanCallback)

        handler.postDelayed({
            bluetoothLeScanner?.stopScan(leScanCallback)
        }, 10000)
    }

    private fun connectToDevice(device: BluetoothDevice) {
        bluetoothGatt = device.connectGatt(
            context,
            false,
            gattCallback,
            BluetoothDevice.TRANSPORT_LE,
            BluetoothDevice.PHY_LE_1M_MASK,
            handler
        )
    }

    private val gattCallback = object : BluetoothGattCallback() {
        override fun onConnectionStateChange(
            gatt: BluetoothGatt, status: Int, newState: Int
        ) {
            super.onConnectionStateChange(gatt, status, newState)
            if (newState == BluetoothProfile.STATE_CONNECTED) {
                Log.d("BluetoothService", "Device connected ${gatt.device.name}")
                onDeviceConnected(gatt.device)
                gatt.discoverServices()
            } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
                Log.d("BluetoothService", "Device disconnected ${gatt.device.name}")
                onDeviceDisconnected(gatt.device)
                gatt.close()
            }
        }

        override fun onServicesDiscovered(gatt: BluetoothGatt, status: Int) {
            super.onServicesDiscovered(gatt, status)
            if (status == BluetoothGatt.GATT_SUCCESS) {
                val service = gatt.getService(SERVICE_UUID)
                if (service != null) {
                    val cTruckInfo = service.getCharacteristic(TRUCK_INFO_UUID)
                    if (cTruckInfo != null) {
                        enableNotifications(gatt, cTruckInfo)
                    } else {
                        Log.e("BluetoothService", "TRUCK INFO characteristic not found")
                    }
                    val cSmartBoxInfo = service.getCharacteristic(SMARTBOX_INFO_UUID)
                    if (cSmartBoxInfo != null) {
                        enableNotifications(gatt, cSmartBoxInfo)
                    } else {
                        Log.e("BluetoothService", "SMARTBOX INFO characteristic not found")
                    }
                } else {
                    Log.e("BluetoothService", "Service not found")
                }
            } else {
                Log.w("BluetoothService", "Failed on discover services: $status")
            }
        }

        override fun onCharacteristicChanged(
            gatt: BluetoothGatt, characteristic: BluetoothGattCharacteristic
        ) {
            super.onCharacteristicChanged(gatt, characteristic)
            val data = characteristic.value
            val message = data?.let { String(it) }
            if (message != null) {
                scope.launch {
                    onDataReceived(characteristic.uuid, message)
                }
            }
        }
    }

    private fun enableNotifications(
        gatt: BluetoothGatt, characteristic: BluetoothGattCharacteristic
    ) {
        gatt.setCharacteristicNotification(characteristic, true)
        val descriptor = characteristic.getDescriptor(DESCRIPTOR_UUID)
        descriptor?.let {
            it.value = BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE
            gatt.writeDescriptor(it)
        }
    }

    private fun onDataReceived(uuid: UUID, data: String) {
        if (uuid == TRUCK_INFO_UUID) {
            Log.d("BluetoothService", "Data received from TRUCK_INFO: $data")

            truckInfoMessageBuilder.addFragment(data)

            val truckInfoJson = truckInfoMessageBuilder.build()
            val truckInfo = Utilities.serializeJsonToTruckInfo(truckInfoJson)
            if (truckInfo != null) {
                Utilities.sendBroadcast(context, BluetoothReceiver.TRUCK_CONNECTED, true)
                Utilities.sendBroadcast(context, BluetoothReceiver.TRUCK_INFO_RECEIVED, truckInfo)

                truckInfoMessageBuilder.reset()
                Log.d("BluetoothService", "Finished received TRUCK_INFO: $truckInfo")
            }
        }
    }

    private fun onDeviceConnected(device: BluetoothDevice) {
        Log.d("BluetoothService", "Device connected: ${device.name}")
    }

    private fun onDeviceDisconnected(device: BluetoothDevice) {
        Utilities.sendBroadcast(context, BluetoothReceiver.TRUCK_CONNECTED, false)
        Log.d("BluetoothService", "Device disconnected: ${device.name}")
        bluetoothGatt = null
    }

    fun stopService() {
        scope.cancel()
        bluetoothGatt?.disconnect()
        bluetoothGatt?.close()
        bluetoothGatt = null
    }
}
