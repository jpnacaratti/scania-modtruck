package com.jpnacaratti.modtruck.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jpnacaratti.modtruck.ui.animations.AboutTruckColorAnimationState
import com.jpnacaratti.modtruck.ui.states.HomeScreenUiState
import com.jpnacaratti.modtruck.ui.theme.LightDarkBlue
import com.jpnacaratti.modtruck.ui.theme.LightGray
import com.jpnacaratti.modtruck.ui.theme.White
import com.jpnacaratti.modtruck.utils.GoogleFontProvider.Companion.poppins
import com.nacaratti.modtruck.R

@Composable
fun TruckColorRow(truckColor: String, state: HomeScreenUiState, animationState: AboutTruckColorAnimationState?, modifier: Modifier = Modifier) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .size(53.dp)
                .clip(shape = RoundedCornerShape(size = 15.dp))
                .background(color = animationState?.truckBackgroundColor ?: LightDarkBlue),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(R.drawable.icon_truck),
                contentDescription = "Truck icon",
                modifier = Modifier.size(33.dp)
            )
        }
        Column(
            modifier = Modifier.padding(start = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Cor",
                color = White,
                fontSize = 17.sp,
                fontFamily = poppins(weight = FontWeight.Medium)
            )
            Text(
                text = truckColor,
                color = LightGray,
                style = state.truckColorTextStyle,
                fontFamily = poppins(weight = FontWeight.Light),
                modifier = Modifier.align(alignment = Alignment.CenterHorizontally).drawWithContent {
                    if (state.truckColorReadyToDraw) drawContent()
                },
                onTextLayout = state.onTruckColorStyleChange
            )
        }
    }
}

@Composable
fun TruckSignRow(truckSign: String, state: HomeScreenUiState, animationState: AboutTruckColorAnimationState?, modifier: Modifier = Modifier) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .size(53.dp)
                .clip(shape = RoundedCornerShape(size = 15.dp))
                .background(color = animationState?.signBackgroundTruck ?: LightDarkBlue),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(R.drawable.icon_truck_sign),
                contentDescription = "Truck sign icon",
                modifier = Modifier.size(33.dp)
            )
        }
        Column(
            modifier = Modifier.padding(start = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Placa",
                color = White,
                fontSize = 17.sp,
                fontFamily = poppins(weight = FontWeight.Medium)
            )
            Text(
                text = truckSign,
                color = LightGray,
                style = state.truckSignTextStyle,
                fontFamily = poppins(weight = FontWeight.Light),
                modifier = Modifier.align(alignment = Alignment.CenterHorizontally).drawWithContent {
                    if (state.truckSignReadyToDraw) drawContent()
                }.align(alignment = Alignment.CenterHorizontally),
                onTextLayout = state.onTruckSignStyleChange
            )
        }
    }
}