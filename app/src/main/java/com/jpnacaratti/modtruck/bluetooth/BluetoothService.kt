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
import com.jpnacaratti.modtruck.models.ModuleInfo
import com.jpnacaratti.modtruck.models.SmartBoxInfo
import com.jpnacaratti.modtruck.models.TruckInfo
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
    private val MODULE_INFO_UUID: UUID = UUID.fromString("abcdef03-1234-5678-1234-56789abcdef3")

    private val DESCRIPTOR_UUID: UUID = UUID.fromString("00002902-0000-1000-8000-00805f9b34fb") // Scania ModTruck UUID

    private var bluetoothGatt: BluetoothGatt? = null
    private val characteristicsToEnable = mutableListOf<BluetoothGattCharacteristic>()

    private var isDeviceConnected = false

    private val truckInfoMessageBuilder: MessageBuilder = MessageBuilder(3)
    private val smartBoxMessageBuilder: MessageBuilder = MessageBuilder(3)
    private val moduleInfoMessageBuilder: MessageBuilder = MessageBuilder(3)

    private val leScanCallback = object : ScanCallback() {
        override fun onScanResult(callbackType: Int, result: ScanResult) {
            super.onScanResult(callbackType, result)
            val device = result.device
            if (device.name == null || isDeviceConnected) return
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
        }, 6000000)
    }

    private fun connectToDevice(device: BluetoothDevice) {
        if (!isDeviceConnected) {
            isDeviceConnected = true
            bluetoothGatt = device.connectGatt(
                context,
                false,
                gattCallback,
                BluetoothDevice.TRANSPORT_LE,
                BluetoothDevice.PHY_LE_1M_MASK,
                handler
            )
        }
    }

    private val gattCallback = object : BluetoothGattCallback() {
        override fun onConnectionStateChange(
            gatt: BluetoothGatt, status: Int, newState: Int
        ) {
            super.onConnectionStateChange(gatt, status, newState)
            if (newState == BluetoothProfile.STATE_CONNECTED) {
                Log.d("BluetoothService", "Device connected ${gatt.device.name}")
                onDeviceConnected(gatt.device)

                bluetoothAdapter?.bluetoothLeScanner?.stopScan(leScanCallback)

                gatt.discoverServices()
            } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
                Log.d("BluetoothService", "Device disconnected ${gatt.device.name}")
                onDeviceDisconnected(gatt.device)
                isDeviceConnected = false
                gatt.close()
            }
        }

        override fun onServicesDiscovered(gatt: BluetoothGatt, status: Int) {
            super.onServicesDiscovered(gatt, status)
            if (status == BluetoothGatt.GATT_SUCCESS) {
                val service = gatt.getService(SERVICE_UUID)
                if (service != null) {
                    val cTruckInfo = service.getCharacteristic(TRUCK_INFO_UUID)
                    val cSmartBoxInfo = service.getCharacteristic(SMARTBOX_INFO_UUID)
                    val cModuleInfo = service.getCharacteristic(MODULE_INFO_UUID)
                    if (cTruckInfo != null) {
                        characteristicsToEnable.add(cTruckInfo)
                    } else {
                        Log.e("BluetoothService", "TRUCK INFO characteristic not found")
                    }
                    if (cSmartBoxInfo != null) {
                        characteristicsToEnable.add(cSmartBoxInfo)
                    } else {
                        Log.e("BluetoothService", "SMARTBOX INFO characteristic not found")
                    }
                    if (cModuleInfo != null) {
                        characteristicsToEnable.add(cModuleInfo)
                    } else {
                        Log.e("BluetoothService", "MODULE INFO characteristic not found")
                    }
                    if (characteristicsToEnable.isNotEmpty()) {
                        enableNextNotification(gatt)
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

        override fun onDescriptorWrite(
            gatt: BluetoothGatt,
            descriptor: BluetoothGattDescriptor,
            status: Int
        ) {
            super.onDescriptorWrite(gatt, descriptor, status)
            if (status == BluetoothGatt.GATT_SUCCESS) {
                characteristicsToEnable.removeFirstOrNull()
                enableNextNotification(gatt)
            } else {
                Log.e("BluetoothService", "Failed to write descriptor: $status")
            }
        }
    }

    private fun enableNextNotification(gatt: BluetoothGatt) {
        if (characteristicsToEnable.isNotEmpty()) {
            val characteristic = characteristicsToEnable.first()
            gatt.setCharacteristicNotification(characteristic, true)
            val descriptor = characteristic.getDescriptor(DESCRIPTOR_UUID)
            descriptor?.let {
                it.value = BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE
                gatt.writeDescriptor(it)
            }
        }
    }

    private fun onDataReceived(uuid: UUID, data: String) {
        //Log.d("BluetoothService", "Data received from ALL: $data")
        when(uuid) {
            SMARTBOX_INFO_UUID -> {
                Log.d("BluetoothService", "Data received from SMARTBOX_INFO: $data")

                smartBoxMessageBuilder.addFragment(data)

                val smartBoxInfoJson = smartBoxMessageBuilder.build()
                val smartBoxInfo = Utilities.serializeJsonToObject<SmartBoxInfo>(smartBoxInfoJson)
                if (smartBoxInfo != null) {
                    Utilities.sendBroadcast(context, BluetoothReceiver.SMARTBOX_INFO_RECEIVED, smartBoxInfo)

                    smartBoxMessageBuilder.reset()
                    Log.d("BluetoothService", "Finished received SMARTBOX_INFO: $smartBoxInfo")
                }
            }
            TRUCK_INFO_UUID -> {
                Log.d("BluetoothService", "Data received from TRUCK_INFO: $data")

                truckInfoMessageBuilder.addFragment(data)

                val truckInfoJson = truckInfoMessageBuilder.build()
                val truckInfo = Utilities.serializeJsonToObject<TruckInfo>(truckInfoJson)
                if (truckInfo != null) {
                    Utilities.sendBroadcast(context, BluetoothReceiver.TRUCK_CONNECTED, true)
                    Utilities.sendBroadcast(context, BluetoothReceiver.TRUCK_INFO_RECEIVED, truckInfo)

                    truckInfoMessageBuilder.reset()
                    Log.d("BluetoothService", "Finished received TRUCK_INFO: $truckInfo")
                }
            }
            MODULE_INFO_UUID -> {
                Log.d("BluetoothService", "Data received from MODULE_INFO: $data")

                moduleInfoMessageBuilder.addFragment(data)

                val moduleInfoJson = moduleInfoMessageBuilder.build()
                val moduleInfo = Utilities.serializeJsonToObject<ModuleInfo>(moduleInfoJson)
                if (moduleInfo != null) {
                    Utilities.sendBroadcast(context, BluetoothReceiver.MODULE_INFO_RECEIVED, moduleInfo)

                    moduleInfoMessageBuilder.reset()
                    Log.d("BluetoothService", "Finished received MODULE_INFO: $moduleInfo")
                }
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
        isDeviceConnected = false
    }

    fun stopService() {
        scope.cancel()
        bluetoothGatt?.disconnect()
        bluetoothGatt?.close()
        bluetoothGatt = null
        isDeviceConnected = false
    }
}
