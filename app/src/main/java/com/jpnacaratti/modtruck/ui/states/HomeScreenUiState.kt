package com.jpnacaratti.modtruck.ui.states

import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp

data class HomeScreenUiState(
    val isFirstCardBlurReady: Boolean = false,
    val truckColorTextStyle: TextStyle = TextStyle(fontSize = 15.sp),
    val truckSignTextStyle: TextStyle = TextStyle(fontSize = 15.sp),
    val truckColorReadyToDraw: Boolean = false,
    val truckSignReadyToDraw: Boolean = false,
    val onFirstCardBlurReady: () -> Unit = {},
    val onConnectButtonClick: () -> Unit = {},
    val onTruckSignStyleChange: (TextLayoutResult) -> Unit = {},
    val onTruckColorStyleChange: (TextLayoutResult) -> Unit = {}
) {
    fun getTruckQualityStatus(): Float {
        return 0.73f // TODO: Calc
    }
}