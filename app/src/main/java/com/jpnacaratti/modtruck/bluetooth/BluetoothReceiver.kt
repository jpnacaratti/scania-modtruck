package com.jpnacaratti.modtruck.bluetooth

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.jpnacaratti.modtruck.models.SmartBoxInfo
import com.jpnacaratti.modtruck.models.TruckInfo
import com.jpnacaratti.modtruck.ui.viewmodels.TruckViewModel

class BluetoothReceiver(private val viewModel: TruckViewModel): BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent == null) return

        val action = intent.action ?: return
        when(action) {
            TRUCK_CONNECTED -> {
                val data = intent.getBooleanExtra(EXTRA_DATA, false)

                viewModel.setTruckConnected(data)
            }
            TRUCK_INFO_RECEIVED -> {
                val data = intent.getSerializableExtra(EXTRA_DATA) as TruckInfo?

                viewModel.updateTruckInfo(data)
            }
            SMARTBOX_INFO_RECEIVED -> {
                val data = intent.getSerializableExtra(EXTRA_DATA) as SmartBoxInfo?

                viewModel.updateSmartBoxInfo(data)
            }
        }
    }

    companion object {
        const val TRUCK_CONNECTED = "TRUCK_CONNECTED"
        const val TRUCK_INFO_RECEIVED = "TRUCK_INFO_RECEIVED"
        const val EXTRA_DATA = "EXTRA_DATA"
        const val SMARTBOX_INFO_RECEIVED = "SMARTBOX_INFO_RECEIVED"
    }
}