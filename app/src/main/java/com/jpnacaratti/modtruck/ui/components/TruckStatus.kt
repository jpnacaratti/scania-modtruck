package com.jpnacaratti.modtruck.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jpnacaratti.modtruck.ui.animations.rememberTruckStatusAnimation
import com.jpnacaratti.modtruck.ui.theme.LightBlue
import com.jpnacaratti.modtruck.ui.theme.LightDarkBlue
import com.jpnacaratti.modtruck.ui.theme.LightDarkGray
import com.jpnacaratti.modtruck.ui.theme.LightGray
import com.jpnacaratti.modtruck.ui.theme.ModTruckTheme
import com.jpnacaratti.modtruck.utils.GoogleFontProvider
import com.jpnacaratti.modtruck.utils.GoogleFontProvider.Companion.poppins

@Composable
fun TruckStatus(progressPercentage: Float, modifier: Modifier = Modifier) {
    val optimizer = 1f - progressPercentage
    val width = 270.dp

    val animationState = rememberTruckStatusAnimation(progressPercentage)

    Column(modifier = modifier) {
        Box(
            modifier = Modifier
                .width(330.dp)
                .height(height = 20.dp),
            contentAlignment = Alignment.BottomStart
        ) {
            Box(
                modifier = Modifier
                    .width(270.dp)
                    .fillMaxHeight()
                    .clip(shape = RoundedCornerShape(size = 5.dp))
                    .background(color = LightDarkBlue)
            ) {
                Box(
                    modifier = Modifier
                        .width((animationState.loadingPercentage * width.value).dp)
                        .fillMaxHeight()
                        .background(
                            brush = Brush.horizontalGradient(
                                colors = listOf(
                                    LightBlue,
                                    LightDarkGray,
                                    LightDarkBlue
                                ),
                                startX = width.value * ((animationState.loadingPercentage * 2) - (optimizer / 8))
                            )
                        )
                )
            }
            if (animationState.showBalloon) {
                TruckStatusBalloon(
                    animationState = animationState,
                    progressPercentage = progressPercentage,
                    width = width
                )
            }
        }
        Text(
            text = "Avaliação geral",
            fontSize = 15.sp,
            color = LightGray,
            fontFamily = poppins(weight = FontWeight.Light),
            modifier = Modifier.padding(top = 7.dp)
        )
    }
}

@Preview
@Composable
private fun TruckStatusPreview() {

    GoogleFontProvider.initialize()

    ModTruckTheme {
        TruckStatus(0.73f)
    }
}