package com.jpnacaratti.modtruck.models

import com.jpnacaratti.modtruck.enums.ModuleStatus
import com.jpnacaratti.modtruck.interfaces.TruckModule

data class BatteryLevelModule(
    override val connected: Boolean = false,
    val status: ModuleStatus = ModuleStatus.OK,
    val batteryLevel: Int = 0
): TruckModule