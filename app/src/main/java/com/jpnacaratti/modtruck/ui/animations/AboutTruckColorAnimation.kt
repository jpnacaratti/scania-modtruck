package com.jpnacaratti.modtruck.ui.animations

import androidx.compose.animation.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.delay

@Composable
fun rememberAboutTruckColorAnimation(duration: Int, startAndEndColor: Color,
                                     truckFinalColor: Color, signFinalColor: Color,
                                     delayStart: Long = 0): AboutTruckColorAnimationState {

    var truckColorBgTransition = remember { Animatable(startAndEndColor) }
    var signColorBgTransition = remember { Animatable(startAndEndColor) }

    LaunchedEffect(Unit) {
        delay(delayStart)
        truckColorBgTransition.animateTo(
            targetValue = truckFinalColor,
            animationSpec = tween(
                durationMillis = duration,
                easing = LinearEasing
            )
        )
        signColorBgTransition.animateTo(
            targetValue = signFinalColor,
            animationSpec = tween(
                durationMillis = duration,
                easing = LinearEasing
            )
        )
        truckColorBgTransition.animateTo(
            targetValue = startAndEndColor,
            animationSpec = tween(
                durationMillis = duration,
                easing = LinearEasing
            )
        )
        signColorBgTransition.animateTo(
            targetValue = startAndEndColor,
            animationSpec = tween(
                durationMillis = duration,
                easing = LinearEasing
            )
        )
    }

    return AboutTruckColorAnimationState(
        truckBackgroundColor = truckColorBgTransition.value,
        signBackgroundTruck = signColorBgTransition.value
    )
}

data class AboutTruckColorAnimationState(
    val truckBackgroundColor: Color,
    val signBackgroundTruck: Color
)