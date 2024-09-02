package com.jpnacaratti.modtruck.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.layout
import androidx.compose.ui.res.painterResource
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
import com.jpnacaratti.modtruck.ui.theme.White
import com.jpnacaratti.modtruck.utils.GoogleFontProvider
import com.jpnacaratti.modtruck.utils.GoogleFontProvider.Companion.poppins
import com.nacaratti.modtruck.R

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
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .layout { measurable, constraints ->
                            val placeable = measurable.measure(
                                constraints.copy(
                                    maxWidth = constraints.maxWidth + 30.dp.roundToPx(),
                                    maxHeight = constraints.maxHeight + 40.dp.roundToPx()
                                )
                            )
                            layout(placeable.width, placeable.height) {
                                placeable.place(0, 0)
                            }
                        }
                        .offset(x = ((animationState.loadingBalloon * width.value).dp - 30.dp), y = (-20).dp)
                        .width(width = 60.dp)
                        .alpha(animationState.alphaBalloon)
                ) {
                    Image(
                        painter = painterResource(R.drawable.truck_status_ballon),
                        contentDescription = "Truck percentage",
                        modifier = Modifier.fillMaxSize()
                    )
                    Text(
                        text = (progressPercentage * 100).toInt().toString(),
                        fontSize = 18.sp,
                        color = White,
                        fontFamily = poppins(weight = FontWeight.Normal),
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                }
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