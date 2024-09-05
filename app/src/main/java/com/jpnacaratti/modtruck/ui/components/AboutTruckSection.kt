package com.jpnacaratti.modtruck.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layout
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jpnacaratti.modtruck.ui.animations.AboutTruckColorAnimationState
import com.jpnacaratti.modtruck.ui.animations.rememberAboutTruckColorAnimation
import com.jpnacaratti.modtruck.ui.states.HomeScreenUiState
import com.jpnacaratti.modtruck.ui.theme.Gray
import com.jpnacaratti.modtruck.ui.theme.LightBlue
import com.jpnacaratti.modtruck.ui.theme.LightDarkBlue
import com.jpnacaratti.modtruck.ui.theme.ModTruckTheme
import com.jpnacaratti.modtruck.ui.theme.Orange
import com.jpnacaratti.modtruck.utils.GoogleFontProvider

@Composable
fun AboutTruckSection(
    truckColor: String,
    truckSign: String,
    truckConnected: Boolean,
    state: HomeScreenUiState,
    modifier: Modifier = Modifier
) {

    var animationState: AboutTruckColorAnimationState? = null
    if (truckConnected) {
        animationState = rememberAboutTruckColorAnimation(
            duration = 500,
            startAndEndColor = LightDarkBlue,
            truckFinalColor = Orange,
            signFinalColor = LightBlue,
            delayStart = 1750
        )
    }

    Row(
        modifier
            .layout { measurable, constraints ->
                val placeable = measurable.measure(
                    constraints.copy(
                        maxWidth = constraints.maxWidth + 20.dp.roundToPx(),
                    )
                )
                layout(placeable.width, placeable.height) {
                    placeable.place(0, 0)
                }
            }
            .padding(top = 4.dp)
            .fillMaxWidth()
            .height(91.dp)
            .border(
                width = 1.dp,
                color = Gray,
                shape = RoundedCornerShape(size = 30.dp)
            )
    ) {
        Row(
            modifier = Modifier
                .padding(vertical = 19.dp, horizontal = 17.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TruckColorRow(
                truckColor = truckColor,
                state = state,
                animationState = animationState,
                modifier = Modifier.width(114.dp)
            )
            VerticalDivider(
                color = Gray,
                modifier = Modifier.padding(vertical = 5.dp, horizontal = 8.dp)
            )
            TruckSignRow(
                truckSign = truckSign,
                state = state,
                animationState = animationState,
                modifier = Modifier.width(128.dp)
            )
        }
    }
}

@Preview
@Composable
private fun AboutTruckSectionPreview() {

    GoogleFontProvider.initialize()

    ModTruckTheme {
        AboutTruckSection(
            truckColor = "Azul",
            truckSign = "ABC-1234",
            truckConnected = true,
            state = HomeScreenUiState()
        )
    }
}