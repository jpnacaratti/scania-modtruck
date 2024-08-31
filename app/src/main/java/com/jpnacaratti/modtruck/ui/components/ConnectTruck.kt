package com.jpnacaratti.modtruck.ui.components

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.RenderEffect
import android.graphics.Shader
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jpnacaratti.modtruck.ui.theme.Grey
import com.jpnacaratti.modtruck.ui.theme.LightGrey
import com.jpnacaratti.modtruck.ui.theme.ModTruckTheme
import com.nacaratti.modtruck.R

val provider = GoogleFont.Provider(
    providerAuthority = "com.google.android.gms.fonts",
    providerPackage = "com.google.android.gms",
    certificates = R.array.com_google_android_gms_fonts_certs
)

val mediumPoppins = FontFamily(
    Font(
        googleFont = GoogleFont("Poppins"),
        fontProvider = provider,
        weight = FontWeight.Medium,
        style = FontStyle.Normal
    )
)

val lightPoppins = FontFamily(
    Font(
        googleFont = GoogleFont("Poppins"),
        fontProvider = provider,
        weight = FontWeight.Light,
        style = FontStyle.Normal
    )
)

@Composable
fun ConnectTruck() {
    var capturedBitmap by remember { mutableStateOf<ImageBitmap?>(null) }
    var layoutCoordinates by remember { mutableStateOf<LayoutCoordinates?>(null) }
    val view = LocalView.current

    Box(
        modifier = Modifier
            .size(330.dp)
            .clip(RoundedCornerShape(40.dp))
            .onGloballyPositioned { coordinates ->
                layoutCoordinates = coordinates
            }
    ) {
        layoutCoordinates?.let { coordinates ->
            LaunchedEffect(coordinates) {
                val position = coordinates.positionInRoot()
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
                    .fillMaxSize()
                    .graphicsLayer {
                        val blurEffect = RenderEffect.createBlurEffect(
                            9f,
                            9f,
                            Shader.TileMode.REPEAT
                        )

                        renderEffect = blurEffect.asComposeRenderEffect()
                    }
                    .drawWithContent {
                        drawContent()

                        drawRect(
                            color = Grey.copy(alpha = 0.8f),
                            size = size
                        )
                    }
            )
        }

        if (capturedBitmap != null) {
            Column(modifier = Modifier.padding(30.dp)) {
                Text(
                    "Overview",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontFamily = mediumPoppins
                )
                Text(
                    "Nenhum caminhão conectado",
                    color = LightGrey,
                    fontSize = 15.sp,
                    fontFamily = lightPoppins
                )
            }
        }
    }
}

fun captureRegionFromView(view: android.view.View, x: Int, y: Int, width: Int, height: Int): Bitmap {
    val bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)
    view.draw(canvas)
    return Bitmap.createBitmap(bitmap, x, y, width, height)
}

@Preview
@Composable
private fun ConnectTruckPreview() {
    ModTruckTheme {
        Surface {
            ConnectTruck()
        }
    }
}