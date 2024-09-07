package com.jpnacaratti.modtruck.ui.animations

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import kotlinx.coroutines.delay

@Composable
fun rememberBaseAlphaAnimation(
    duration: Int,
    initialAlpha: Float = 0f,
    finalAlpha: Float = 1f,
    delayStart: Long = 0,
    onFinish: () -> Unit = {}
): BaseAlphaAnimationState {

    val alpha = remember { Animatable(initialAlpha) }

    LaunchedEffect(Unit) {
        delay(delayStart)

        alpha.animateTo(
            targetValue = finalAlpha,
            animationSpec = tween(
                durationMillis = duration,
                easing = LinearEasing
            )
        )

        onFinish()
    }

    return BaseAlphaAnimationState(
        alpha = alpha.value
    )
}

data class BaseAlphaAnimationState(
    val alpha: Float
)