package com.jpnacaratti.modtruck.utils

import android.content.Context
import android.content.SharedPreferences

class UserPreferences(private val context: Context) {

    private val HAS_CONNECTED_BEFORE = "HAS_CONNECTED_BEFORE"

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

    fun clear() {
        prefEditor.clear()
        prefEditor.apply()
    }

}