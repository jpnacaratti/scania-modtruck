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
import com.jpnacaratti.modtruck.bluetooth.BluetoothReceiver
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

    val truckColor: String = truckInfo?.color ?: "-/-"
    val truckSign: String = truckInfo?.sign ?: "-/-"

    val truckModel: String = truckInfo?.model ?: "Nenhum caminhão conectado"

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
                        val intent = Intent(BluetoothReceiver.TRUCK_CONNECTED).apply {
                            putExtra(BluetoothReceiver.EXTRA_DATA, true)
                        }
                        context.sendBroadcast(intent)

                        val truckInfoIntent = Intent(BluetoothReceiver.TRUCK_INFO_RECEIVED).apply {
                            putExtra(
                                BluetoothReceiver.EXTRA_DATA, TruckInfo(
                                    color = "Laranja",
                                    sign = "ABC-1234",
                                    model = "Scania 620S V8"
                                )
                            )
                        }
                        context.sendBroadcast(truckInfoIntent)

                        val smartboxInfoIntent = Intent(BluetoothReceiver.SMARTBOX_INFO_RECEIVED).apply {
                            putExtra(
                                BluetoothReceiver.EXTRA_DATA, SmartBoxInfo(
                                    model = "SmartBox P6 3.0",
                                    serial = "D8P28VMXJA",
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
        color = "Laranja",
        sign = "ABC-1234",
        model = "Scania 620S V8"
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