package com.jpnacaratti.modtruck.models

import androidx.compose.ui.graphics.Color

data class ModuleStats(
    val icon: Int,
    val title: String,
    val showDescription: String,
    val information: String,
    val statusIcon: Int,
    val statusIconColor: Color,
    val statusLevel: String,
    val statusDescription: String,
    val attributes: List<ModuleAttribute>
)