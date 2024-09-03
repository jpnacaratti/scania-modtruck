package com.jpnacaratti.modtruck.models

import com.jpnacaratti.modtruck.enums.ModuleStatus
import com.jpnacaratti.modtruck.interfaces.TruckModule

data class EngineSoundModule(
    override val connected: Boolean = false,
    val status: ModuleStatus = ModuleStatus.OK
): TruckModule {

    fun getCommonName(): String {
        return when (status) {
            ModuleStatus.OK -> "Sem ruidos anormais"
            ModuleStatus.WARNING -> "Ruidos anormais detectados"
            ModuleStatus.ERROR -> "N/A" // TODO: Implement control for this time
        }
    }
}
