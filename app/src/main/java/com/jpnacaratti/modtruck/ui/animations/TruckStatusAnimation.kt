package com.jpnacaratti.modtruck.ui.animations

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun rememberTruckStatusAnimation(progressPercentage: Float, delayStart: Long = 0): TruckStatusAnimationState {

    val startingLoadingBalloonPercentage = progressPercentage - (progressPercentage * 0.64f) // 0.45
    val firstLoadingPercentage = progressPercentage - (progressPercentage * 0.40f) // 0.30
    val showBalloonPercentage = progressPercentage - (progressPercentage * 0.47f) // 0.40

    val loadingPercentage = remember { Animatable(0f) }
    val loadingBalloon = remember { Animatable(startingLoadingBalloonPercentage) } // 64% (70)
    val alphaBalloon = remember { Animatable(0f) }

    var showBalloon by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(delayStart)
        loadingPercentage.animateTo(
            targetValue = firstLoadingPercentage, // -40% (70)
            animationSpec = tween(durationMillis = 1500)
        ) {
            if (loadingPercentage.value > showBalloonPercentage){ // -57% (70)
                showBalloon = true
                launch {
                    alphaBalloon.animateTo(
                        targetValue = 0.8f,
                        animationSpec = tween(durationMillis = 500)
                    )
                }
            }
        }
        launch {
            loadingPercentage.animateTo(
                targetValue = progressPercentage,
                animationSpec = tween(durationMillis = 1000)
            )
        }
        launch {
            loadingBalloon.animateTo(
                targetValue = progressPercentage,
                animationSpec = tween(durationMillis = 2000)
            )
        }
        alphaBalloon.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 2000)
        )
    }

    return TruckStatusAnimationState(
        loadingPercentage = loadingPercentage.value,
        loadingBalloon = loadingBalloon.value,
        alphaBalloon = alphaBalloon.value,
        showBalloon = showBalloon
    )
}

data class TruckStatusAnimationState(
    val loadingPercentage: Float,
    val loadingBalloon: Float,
    val alphaBalloon: Float,
    val showBalloon: Boolean
)
