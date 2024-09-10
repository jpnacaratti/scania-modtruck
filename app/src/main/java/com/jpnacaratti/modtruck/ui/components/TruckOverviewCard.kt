package com.jpnacaratti.modtruck.ui.components

import android.content.Intent
import androidx.compose.foundation.background
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jpnacaratti.modtruck.bluetooth.BluetoothService.Companion.EXTRA_DATA
import com.jpnacaratti.modtruck.bluetooth.BluetoothService.Companion.SMARTBOX_INFO_RECEIVED
import com.jpnacaratti.modtruck.bluetooth.BluetoothService.Companion.TRUCK_CONNECTED
import com.jpnacaratti.modtruck.bluetooth.BluetoothService.Companion.TRUCK_INFO_RECEIVED
import com.jpnacaratti.modtruck.models.SmartBoxInfo
import com.jpnacaratti.modtruck.models.TruckInfo
import com.jpnacaratti.modtruck.ui.states.HomeScreenUiState
import com.jpnacaratti.modtruck.ui.theme.DarkGray
import com.jpnacaratti.modtruck.ui.theme.LightGray
import com.jpnacaratti.modtruck.ui.theme.ModTruckTheme
import com.jpnacaratti.modtruck.ui.theme.White
import com.jpnacaratti.modtruck.utils.GoogleFontProvider
import com.jpnacaratti.modtruck.utils.GoogleFontProvider.Companion.poppins

@Composable
fun TruckOverviewCard(
    truckConnected: Boolean,
    state: HomeScreenUiState,
    truckInfo: TruckInfo?,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    val truckColor: String = truckInfo?.truckColor ?: "-/-"
    val truckSign: String = truckInfo?.truckSign ?: "-/-"

    val truckModel: String = truckInfo?.truckModel ?: "Nenhum caminhão conectado"

    Box(
        modifier = modifier
            .size(size = 330.dp)
            .clip(shape = RoundedCornerShape(40.dp))
            .background(color = DarkGray)
            .alpha(0.8f)
    ) {

        Column(
            modifier = Modifier
                .padding(30.dp)
                .fillMaxWidth()
                .alpha(1f)
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

            if (!truckConnected) {
                ConnectTruckButton(
                    modifier = Modifier
                        .padding(vertical = 20.dp)
                        .align(alignment = Alignment.CenterHorizontally),
                    onClickListener = {

                        // TODO: Remove this and change only when truck is connected
                        val intent = Intent(TRUCK_CONNECTED).apply {
                            putExtra(EXTRA_DATA, true)
                        }
                        context.sendBroadcast(intent)

                        val truckInfoIntent = Intent(TRUCK_INFO_RECEIVED).apply {
                            putExtra(
                                EXTRA_DATA, TruckInfo(
                                    truckColor = "Laranja",
                                    truckSign = "ABC-1234",
                                    truckModel = "Scania 620S V8"
                                )
                            )
                        }
                        context.sendBroadcast(truckInfoIntent)

                        val smartboxInfoIntent = Intent(SMARTBOX_INFO_RECEIVED).apply {
                            putExtra(
                                EXTRA_DATA, SmartBoxInfo(
                                    model = "SmartBox P6 3.0",
                                    serialNumber = "D8P28VMXJA",
                                    numPorts = 6,
                                    usedPorts = 3
                                )
                            )
                        }
                        context.sendBroadcast(smartboxInfoIntent)

                        state.onConnectButtonClick
                    }
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

            AboutTruckSection(
                truckColor = truckColor,
                truckSign = truckSign,
                truckConnected = truckConnected,
                state = state
            )
        }
    }
}


@Preview
@Composable
private fun TruckOverviewCardPreview() {
    val state = HomeScreenUiState(
        isFirstCardBlurReady = true
    )
    val truckInfo = TruckInfo(
        truckColor = "Laranja",
        truckSign = "ABC-1234",
        truckModel = "Scania 620S V8"
    )

    GoogleFontProvider.initialize()

    ModTruckTheme {
        Surface(
            color = DarkGray
        ) {
            TruckOverviewCard(truckConnected = true, state = state, truckInfo = truckInfo)
        }
    }
}