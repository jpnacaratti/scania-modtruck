package com.jpnacaratti.modtruck.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jpnacaratti.modtruck.ui.theme.LightBlue
import com.jpnacaratti.modtruck.ui.theme.LightDarkBlue
import com.jpnacaratti.modtruck.ui.theme.LightDarkGray
import com.jpnacaratti.modtruck.ui.theme.ModTruckTheme
import com.jpnacaratti.modtruck.ui.theme.White
import com.jpnacaratti.modtruck.ui.viewmodels.AppUiState
import com.jpnacaratti.modtruck.utils.GoogleFontProvider
import com.jpnacaratti.modtruck.utils.GoogleFontProvider.Companion.poppins
import com.nacaratti.modtruck.R

@Composable
fun TruckStatus(modifier: Modifier = Modifier, state: AppUiState = AppUiState()) {
    val progressPercentage = 1f
    val optimizer = 1f - progressPercentage

    val width = 270.dp
    val modifiedWidth = (width.value * progressPercentage).dp

    Column(modifier = modifier.width(width)) {
        Box(modifier = Modifier
            .width(330.dp)
            .height(height = 64.dp),
            contentAlignment = Alignment.BottomStart
        ) {
            Box(
                modifier = Modifier
                    .width(270.dp)
                    .height(20.dp)
                    .clip(shape = RoundedCornerShape(size = 5.dp))
                    .background(color = LightDarkBlue)
            ) {
                Box(
                    modifier = Modifier
                        .width(modifiedWidth)
                        .fillMaxHeight()
                        .background(
                            brush = Brush.horizontalGradient(
                                colors = listOf(
                                    LightBlue,
                                    LightDarkGray,
                                    LightDarkBlue
                                ),
                                startX = width.value * ((progressPercentage * 2) - (optimizer / 8))
                            )
                        )
                )
            }
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .offset(x = (modifiedWidth - 30.dp))
                    .size(width = 60.dp, height = 64.dp)
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
}

@Preview
@Composable
private fun TruckStatusPreview() {

    GoogleFontProvider.initialize()

    ModTruckTheme {
        TruckStatus()
    }
}