package com.jpnacaratti.modtruck.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.jpnacaratti.modtruck.enums.ModuleStatus
import com.jpnacaratti.modtruck.models.BatteryLevelModule
import com.jpnacaratti.modtruck.models.EngineHealthModule
import com.jpnacaratti.modtruck.models.EngineSoundModule
import com.jpnacaratti.modtruck.models.TruckInfo
import com.nacaratti.modtruck.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class TruckViewModel : ViewModel() {

    // TODO: Remove these connect = true
    private val _batteryLevelModule =
        MutableStateFlow(BatteryLevelModule(connected = true, batteryLevel = 75))
    val batteryLevelModule: StateFlow<BatteryLevelModule> = _batteryLevelModule.asStateFlow()

    private val _engineSoundModule = MutableStateFlow(EngineSoundModule(connected = true))
    val engineSoundModule: StateFlow<EngineSoundModule> = _engineSoundModule.asStateFlow()

    private val _engineHealthModule =
        MutableStateFlow(EngineHealthModule(connected = true, rpm = 3000, temperature = 80F))
    val engineHealthModule: StateFlow<EngineHealthModule> = _engineHealthModule.asStateFlow()

    private val _truckInfo = MutableStateFlow<TruckInfo?>(null)
    val truckInfo: StateFlow<TruckInfo?> = _truckInfo.asStateFlow()

    private val _truckConnected = MutableStateFlow(false)
    val truckConnected: StateFlow<Boolean> = _truckConnected.asStateFlow()

    fun setTruckConnected(value: Boolean) {
        _truckConnected.value = value
    }

    fun updateTruckInfo(truckInfo: TruckInfo?) {
        _truckInfo.value = truckInfo
    }

    fun getConnectedModulesCount(): Int {
        return listOf(
            batteryLevelModule.value.connected,
            engineSoundModule.value.connected,
            engineHealthModule.value.connected
        ).count { it }
    }

    fun getActiveTimeFormatted(): String {

        // TODO: Get the real time from the truck subtract millis from it

        val finalTime = 5540000L

        val hours = finalTime / (1000 * 60 * 60)
        val minutes = (finalTime / (1000 * 60)) % 60
        val seconds = (finalTime / 1000) % 60

        return "${hours}hr ${minutes}m ${seconds}s"
    }

    fun getAllModulesStatusIcon(): Int {
        val statuses = listOf(
            batteryLevelModule.value.status,
            engineSoundModule.value.status,
            engineHealthModule.value.status
        )

        return when {
            ModuleStatus.ERROR in statuses -> R.drawable.icon_error
            ModuleStatus.WARNING in statuses -> R.drawable.icon_warning
            else -> R.drawable.icon_check
        }
    }

    fun getAllModulesStatusDescription(): String {
        val statuses = listOf(
            batteryLevelModule.value.status,
            engineSoundModule.value.status,
            engineHealthModule.value.status
        )

        return when {
            ModuleStatus.ERROR in statuses -> "Alguns módulos estão em situação crítica!"
            ModuleStatus.WARNING in statuses -> "Alguns módulos precisam de atenção!"
            else -> "Tudo em ordem com o seu caminhão"
        }
    }

}