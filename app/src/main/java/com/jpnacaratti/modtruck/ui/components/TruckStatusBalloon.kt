package com.jpnacaratti.modtruck.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.layout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jpnacaratti.modtruck.ui.animations.TruckStatusAnimationState
import com.jpnacaratti.modtruck.ui.theme.White
import com.jpnacaratti.modtruck.utils.GoogleFontProvider.Companion.poppins
import com.nacaratti.modtruck.R

@Composable
fun TruckStatusBalloon(animationState: TruckStatusAnimationState, progressPercentage: Float, width: Dp, modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
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

@Preview
@Composable
private fun TruckStatusBalloonPreview() {
    val animationState = TruckStatusAnimationState(
        loadingPercentage = 0.73f,
        loadingBalloon = 0.73f,
        alphaBalloon = 1f,
        showBalloon = true
    )

    TruckStatusBalloon(animationState, 0.73f, 270.dp)
}