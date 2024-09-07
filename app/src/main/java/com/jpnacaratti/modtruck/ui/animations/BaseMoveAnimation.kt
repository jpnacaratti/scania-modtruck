package com.jpnacaratti.modtruck.ui.animations

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import kotlinx.coroutines.delay
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch

@Composable
fun rememberBaseMoveAnimation(
    duration: Int,
    initialX: Float? = null,
    initialY: Float? = null,
    finalX: Float = 0F,
    finalY: Float = 0F,
    easing: Easing = FastOutSlowInEasing,
    delayStart: Long = 0,
    onFinish: () -> Unit = {}
): BaseMoveAnimationState {

    val compOffsetX = remember { Animatable((initialX ?: 0).toFloat()) }
    val compOffsetY = remember { Animatable((initialY ?: 0).toFloat()) }

    LaunchedEffect(Unit) {
        delay(delayStart)

        val xAnimation = if (initialX != null) {
            launch {
                compOffsetX.animateTo(
                    targetValue = finalX,
                    animationSpec = tween(
                        durationMillis = duration,
                        easing = FastOutSlowInEasing
                    )
                )
            }
        } else null


        val yAnimation = if (initialY != null) {
            launch {
                compOffsetY.animateTo(
                    targetValue = finalY,
                    animationSpec = tween(
                        durationMillis = duration,
                        easing = FastOutSlowInEasing
                    )
                )
            }
        } else null

        xAnimation?.join()
        yAnimation?.join()

        onFinish()
    }

    return BaseMoveAnimationState(
        compOffsetX = compOffsetX.value,
        compOffsetY = compOffsetY.value
    )
}

data class BaseMoveAnimationState(
    val compOffsetX: Float,
    val compOffsetY: Float
)