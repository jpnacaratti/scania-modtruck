package com.jpnacaratti.modtruck.models

import androidx.compose.ui.graphics.Color

data class ModuleStatusInfo(
    val icon: Int,
    val iconColor: Color,
    val messageLevel: String = "",
    val description: String = "",
)