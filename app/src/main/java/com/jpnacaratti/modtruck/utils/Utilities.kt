package com.jpnacaratti.modtruck.utils

import android.content.Context
import android.content.Intent
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.jpnacaratti.modtruck.bluetooth.BluetoothReceiver
import com.jpnacaratti.modtruck.models.TruckInfo
import java.io.Serializable

class Utilities {

    companion object {
        fun getEmptySampleArray(size: Int): FloatArray {
            val sampleArray = FloatArray(size)
            for (i in 0 until size) {
                sampleArray[i] = 0f
            }
            return sampleArray
        }

        fun serializeJsonToTruckInfo(json: String): TruckInfo? {
            val gson = Gson()
            return try {
                gson.fromJson(json, TruckInfo::class.java)
            } catch (e: JsonSyntaxException) {
                null
            }
        }

        inline fun <reified T> sendBroadcast(context: Context, broadcastName: String, extra: T) {
            val intent = Intent(broadcastName).apply {
                when (extra) {
                    is Boolean -> putExtra(BluetoothReceiver.EXTRA_DATA, extra)
                    is Int -> putExtra(BluetoothReceiver.EXTRA_DATA, extra)
                    is String -> putExtra(BluetoothReceiver.EXTRA_DATA, extra)
                    is Serializable -> putExtra(BluetoothReceiver.EXTRA_DATA, extra)
                    else -> throw IllegalArgumentException("Type not supported")
                }
            }
            context.sendBroadcast(intent)
        }
    }

}