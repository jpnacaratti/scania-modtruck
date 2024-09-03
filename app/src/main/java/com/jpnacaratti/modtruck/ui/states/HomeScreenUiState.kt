package com.jpnacaratti.modtruck.ui.states

import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import com.jpnacaratti.modtruck.models.TruckInfo

data class HomeScreenUiState(
    val isTruckConnected: Boolean = false,
    val isFirstCardBlurReady: Boolean = false,
    val isTruckInfo: TruckInfo? = null,
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