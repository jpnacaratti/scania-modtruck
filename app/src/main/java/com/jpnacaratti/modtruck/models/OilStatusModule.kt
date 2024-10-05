package com.jpnacaratti.modtruck.models

import com.jpnacaratti.modtruck.enums.ModuleStatus
import com.jpnacaratti.modtruck.interfaces.TruckModule

data class OilStatusModule(
    override val connected: Boolean = false,
    val status: ModuleStatus = ModuleStatus.OK,
    val oilLevel: Int = 0
): TruckModule
