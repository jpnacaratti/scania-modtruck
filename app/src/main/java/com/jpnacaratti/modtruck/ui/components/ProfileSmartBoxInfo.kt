package com.jpnacaratti.modtruck.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jpnacaratti.modtruck.models.SmartBoxInfo
import com.jpnacaratti.modtruck.ui.theme.LightGray
import com.jpnacaratti.modtruck.ui.theme.ModTruckTheme
import com.jpnacaratti.modtruck.ui.theme.White
import com.jpnacaratti.modtruck.utils.GoogleFontProvider
import com.jpnacaratti.modtruck.utils.GoogleFontProvider.Companion.poppins

@Composable
fun ProfileSmartBoxInfo(
    connected: Boolean,
    smartBoxInfo: SmartBoxInfo,
    modifier: Modifier = Modifier
) {
    val keyStyle = SpanStyle(
        color = White,
        fontFamily = poppins(FontWeight.Normal),
        fontSize = 15.sp
    )

    val valueStyle = SpanStyle(
        color = LightGray,
        fontFamily = poppins(FontWeight.Light),
        fontSize = 14.sp
    )

    Column(
        modifier = modifier
    ) {
        Text(
            text = "Informações SmartBox",
            fontFamily = poppins(weight = FontWeight.Normal),
            fontSize = 17.sp,
            color = White,
            modifier = Modifier.padding(bottom = 15.dp)
        )
        Column(
            modifier = Modifier.height(height = 145.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = buildAnnotatedString {
                        withStyle(
                            style = keyStyle
                        ) {
                            append("Modelo: ")
                        }
                        withStyle(
                            style = valueStyle
                        ) {
                            append(smartBoxInfo.model)
                        }
                    }
                )
                Text(
                    text = buildAnnotatedString {
                        withStyle(
                            style = keyStyle
                        ) {
                            append("Número de série: ")
                        }
                        withStyle(
                            style = valueStyle
                        ) {
                            append(smartBoxInfo.serial)
                        }
                    }
                )
            }
            Column {
                Text(
                    text = buildAnnotatedString {
                        withStyle(
                            style = keyStyle
                        ) {
                            append("Número de portas: ")
                        }
                        withStyle(
                            style = valueStyle
                        ) {
                            append(smartBoxInfo.numPorts.toString())
                        }
                    }
                )
                Text(
                    text = buildAnnotatedString {
                        withStyle(
                            style = keyStyle
                        ) {
                            append("Portas usadas: ")
                        }
                        withStyle(
                            style = valueStyle
                        ) {
                            append(smartBoxInfo.usedPorts.toString())
                        }
                    }
                )
            }
            Text(
                text = buildAnnotatedString {
                    withStyle(
                        style = keyStyle
                    ) {
                        append("Status: ")
                    }
                    withStyle(
                        style = valueStyle
                    ) {
                        append(if (connected) "CONECTADO" else "DESCONECTADO")
                    }
                }
            )
        }
    }
}

@Preview
@Composable
private fun ProfileSmartBoxInfoPreview() {
    GoogleFontProvider.initialize()

    val smartBoxInfo = SmartBoxInfo(
        model = "SmartBox P6 3.0",
        serial = "D8P28VMXJA",
        numPorts = 6,
        usedPorts = 3
    )

    ModTruckTheme {
        ProfileSmartBoxInfo(
            connected = true,
            smartBoxInfo = smartBoxInfo
        )
    }
}