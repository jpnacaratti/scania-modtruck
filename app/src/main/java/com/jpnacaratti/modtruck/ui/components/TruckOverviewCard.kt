package com.jpnacaratti.modtruck.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jpnacaratti.modtruck.extensions.BackgroundBlurLayer
import com.jpnacaratti.modtruck.models.TruckInfo
import com.jpnacaratti.modtruck.ui.states.HomeScreenUiState
import com.jpnacaratti.modtruck.ui.theme.DarkGray
import com.jpnacaratti.modtruck.ui.theme.LightGray
import com.jpnacaratti.modtruck.ui.theme.ModTruckTheme
import com.jpnacaratti.modtruck.ui.theme.White
import com.jpnacaratti.modtruck.utils.GoogleFontProvider
import com.jpnacaratti.modtruck.utils.GoogleFontProvider.Companion.poppins

@Composable
fun TruckOverviewCard(state: HomeScreenUiState, modifier: Modifier = Modifier) {

    val truckColor: String = state.isTruckInfo?.truckColor ?: "-/-"
    val truckSign: String = state.isTruckInfo?.truckSign ?: "-/-"

    val truckModel: String = state.isTruckInfo?.truckModel ?: "Nenhum caminhão conectado"

    Box(
        modifier = modifier
            .size(size = 330.dp)
            .clip(shape = RoundedCornerShape(40.dp))
    ) {
        BackgroundBlurLayer(blurRadius = 9,
            backgroundColor = DarkGray,
            backgroundColorAlpha = 0.8f,
            state.onFirstCardBlurReady
        )

        if (!state.isFirstCardBlurReady) return

        Column(
            modifier = Modifier
                .padding(30.dp)
                .fillMaxWidth()
        ) {
            Text(
                "Overview",
                color = White,
                fontSize = 18.sp,
                fontFamily = poppins(weight = FontWeight.Medium)
            )
            Text(
                truckModel,
                color = LightGray,
                fontSize = 15.sp,
                fontFamily = poppins(weight = FontWeight.Light)
            )

            if (!state.isTruckConnected) {
                ConnectTruckButton(
                    modifier = Modifier
                        .padding(vertical = 20.dp)
                        .align(alignment = Alignment.CenterHorizontally),
                    onClickListener = state.onConnectButtonClick
                )
            } else {
                TruckStatus(
                    progressPercentage = state.getTruckQualityStatus(),
                    modifier = Modifier.padding(vertical = 20.dp)
                )
            }

            Text(
                text = "Sobre o caminhão",
                fontSize = 18.sp,
                color = White,
                fontFamily = poppins(weight = FontWeight.Medium)
            )

            AboutTruckSection(truckColor = truckColor, truckSign = truckSign, state = state)
        }
    }
}


@Preview
@Composable
private fun TruckOverviewCardPreview() {
    val state = HomeScreenUiState(
        isFirstCardBlurReady = true,
        isTruckConnected = true,
        isTruckInfo = TruckInfo(
            truckColor = "Laranja",
            truckSign = "ABC-1234",
            truckModel = "Scania 620S V8"
        )
    )

    GoogleFontProvider.initialize()

    ModTruckTheme {
        Surface(
            color = DarkGray
        ) {
            TruckOverviewCard(state)
        }
    }
}