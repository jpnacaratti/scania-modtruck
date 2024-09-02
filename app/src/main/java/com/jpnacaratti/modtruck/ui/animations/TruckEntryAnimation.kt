package com.jpnacaratti.modtruck.ui.animations

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@Composable
fun rememberTruckEntryAnimation(duration: Int): TruckEntryAnimationState {
    val truckOffsetX = remember { Animatable(310.dp.value) }
    val truckOffsetY = remember { Animatable(-145.dp.value) }

    LaunchedEffect(Unit) {
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

    return TruckEntryAnimationState(
        truckOffsetX = truckOffsetX.value,
        truckOffsetY = truckOffsetY.value
    )
}

data class TruckEntryAnimationState(
    val truckOffsetX: Float,
    val truckOffsetY: Float
)