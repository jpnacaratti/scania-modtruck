package com.jpnacaratti.modtruck.extensions

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.RenderEffect
import android.graphics.Shader
import android.view.View
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asComposeRenderEffect
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInWindow
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.IntOffset

@Composable
fun Modifier.backgroundBlur(
    blurRadius: Int,
    shape: RoundedCornerShape,
    backgroundColor: Color,
    backgroundColorAlpha: Float = 1f,
    onBlurReady: () -> Unit = {}
): Modifier {
    var capturedBitmap by remember { mutableStateOf<ImageBitmap?>(null) }
    var layoutCoordinates by remember { mutableStateOf<LayoutCoordinates?>(null) }
    val view = LocalView.current

    layoutCoordinates?.let { coordinates ->
        LaunchedEffect(coordinates) {
            val position = coordinates.positionInWindow()

            val bitmap = captureRegionFromView(
                view,
                position.x.toInt(),
                position.y.toInt(),
                coordinates.size.width,
                coordinates.size.height
            )
            capturedBitmap = bitmap.asImageBitmap()
        }
    }

    capturedBitmap?.let {
        Image(
            bitmap = it,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            filterQuality = FilterQuality.Low,
            modifier = Modifier
                .offset {
                    val offsetX = layoutCoordinates?.positionInWindow()?.x?.toInt() ?: 0
                    val offsetY = layoutCoordinates?.positionInWindow()?.y?.toInt() ?: 0
                    IntOffset(x = offsetX, y = offsetY)
                }
                .clip(shape)
                .graphicsLayer {
                    val blurEffect = RenderEffect.createBlurEffect(
                        blurRadius.toFloat(),
                        blurRadius.toFloat(),
                        Shader.TileMode.REPEAT
                    )

                    renderEffect = blurEffect.asComposeRenderEffect()


                }
                .drawWithContent {
                    drawContent()

                    drawRect(
                        color = backgroundColor.copy(alpha = backgroundColorAlpha),
                        size = size
                    )

                    onBlurReady()
                }
        )
    }

    return this then Modifier.onGloballyPositioned { coordinates ->
        layoutCoordinates = coordinates
    }
}

private fun captureRegionFromView(view: View, x: Int, y: Int, width: Int, height: Int): Bitmap {
    val bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)
    view.draw(canvas)
    return Bitmap.createBitmap(bitmap, x, y, width, height)
}

