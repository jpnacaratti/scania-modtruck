package com.jpnacaratti.modtruck.ui.states

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jpnacaratti.modtruck.models.TruckInfo
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

data class HomeScreenUiState(
    val isTruckConnected: Boolean = false,
    val isBlurReady: Boolean = false,
    val isTruckInfo: TruckInfo? = null,
    val truckColorTextStyle: TextStyle = TextStyle(fontSize = 15.sp),
    val truckSignTextStyle: TextStyle = TextStyle(fontSize = 15.sp),
    val truckColorReadyToDraw: Boolean = false,
    val truckSignReadyToDraw: Boolean = false,
    val truckOffsetX: Animatable<Float, AnimationVector1D> = Animatable(310.dp.value),
    val truckOffsetY: Animatable<Float, AnimationVector1D> = Animatable(-145.dp.value),
    val onBlurReady: () -> Unit = {},
    val onConnectButtonClick: () -> Unit = {},
    val onTruckSignStyleChange: (TextLayoutResult) -> Unit = {},
    val onTruckColorStyleChange: (TextLayoutResult) -> Unit = {}
) {
    fun getTruckQualityStatus(): Float {
        return 0.73f // TODO: Calc
    }

    suspend fun startTruckEntryAnimation(duration: Int) {
        coroutineScope {
            launch {
                truckOffsetX.animateTo(
                    targetValue = 8.dp.value,
                    animationSpec = tween(
                        durationMillis = duration,
                        easing = LinearOutSlowInEasing
                    )
                )
            }
            launch {
                truckOffsetY.animateTo(
                    targetValue = 0.dp.value,
                    animationSpec = tween(
                        durationMillis = duration,
                        easing = LinearOutSlowInEasing
                    )
                )
            }
        }
    }
}