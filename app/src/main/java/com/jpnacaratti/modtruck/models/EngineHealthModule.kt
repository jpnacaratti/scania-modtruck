package com.jpnacaratti.modtruck.models

import com.jpnacaratti.modtruck.enums.ModuleStatus
import com.jpnacaratti.modtruck.interfaces.TruckModule

data class EngineHealthModule(
    override val connected: Boolean = false,
    val status: ModuleStatus = ModuleStatus.OK,
    val rpm: Int = 0,
    val temperature: Float = 0f,
): TruckModule {
    val engineWear: Float
        get() = 100f - (temperature * 0.025 + (rpm * 0.005)).toFloat() // TODO: Generate random values
}
