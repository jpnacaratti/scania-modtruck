package com.jpnacaratti.modtruck.utils

import android.content.Context
import android.content.SharedPreferences

class UserPreferences(context: Context) {

    private val HAS_CONNECTED_BEFORE = "HAS_CONNECTED_BEFORE"
    private val SMARTBOX_MODEL = "SMARTBOX_MODEL"
    private val SMARTBOX_SERIAL = "SMARTBOX_SERIAL"
    private val SMARTBOX_NUM_PORTS = "SMARTBOX_NUM_PORTS"
    private val SMARTBOX_USED_PORTS = "SMARTBOX_USED_PORTS"

    private val prefs: SharedPreferences =
        context.getSharedPreferences("SHARED_PREFS", Context.MODE_PRIVATE)
    private val prefEditor: SharedPreferences.Editor = prefs.edit()

    fun saveHasConnectBefore(hasConnectedBefore: Boolean) {
        prefEditor.putBoolean(HAS_CONNECTED_BEFORE, hasConnectedBefore)
        prefEditor.apply()
    }

    fun getHasConnectedBefore(): Boolean {
        return prefs.getBoolean(HAS_CONNECTED_BEFORE, false)
    }

    fun saveSmartBoxModel(model: String) {
        prefEditor.putString(SMARTBOX_MODEL, model)
        prefEditor.apply()
    }

    fun getSmartBoxModel(): String? {
        return prefs.getString(SMARTBOX_MODEL, null)
    }

    fun saveSmartBoxSerial(model: String) {
        prefEditor.putString(SMARTBOX_SERIAL, model)
        prefEditor.apply()
    }

    fun getSmartBoxSerial(): String? {
        return prefs.getString(SMARTBOX_SERIAL, null)
    }

    fun saveSmartBoxNumPorts(ports: Int) {
        prefEditor.putInt(SMARTBOX_NUM_PORTS, ports)
        prefEditor.apply()
    }

    fun getSmartBoxNumPorts(): Int {
        return prefs.getInt(SMARTBOX_NUM_PORTS, 0)
    }

    fun saveSmartBoxUsedPorts(ports: Int) {
        prefEditor.putInt(SMARTBOX_USED_PORTS, ports)
        prefEditor.apply()
    }

    fun getSmartBoxUsedPorts(): Int {
        return prefs.getInt(SMARTBOX_USED_PORTS, 0)
    }

    fun clear() {
        prefEditor.clear()
        prefEditor.apply()
    }

}