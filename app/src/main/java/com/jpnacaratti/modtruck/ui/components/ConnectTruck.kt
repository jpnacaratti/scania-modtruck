package com.jpnacaratti.modtruck.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jpnacaratti.modtruck.extensions.backgroundBlur
import com.jpnacaratti.modtruck.ui.theme.DarkGray
import com.jpnacaratti.modtruck.ui.theme.Gray
import com.jpnacaratti.modtruck.ui.theme.LightBlue
import com.jpnacaratti.modtruck.ui.theme.LightDarkBlue
import com.jpnacaratti.modtruck.ui.theme.LightGray
import com.jpnacaratti.modtruck.ui.theme.ModTruckTheme
import com.nacaratti.modtruck.R

val provider = GoogleFont.Provider(
    providerAuthority = "com.google.android.gms.fonts",
    providerPackage = "com.google.android.gms",
    certificates = R.array.com_google_android_gms_fonts_certs
)

fun poppins(weight: FontWeight, style: FontStyle = FontStyle.Normal): FontFamily = FontFamily(
    Font(
        googleFont = GoogleFont("Poppins"),
        fontProvider = provider,
        weight = weight,
        style = style
    )
)


@Composable
fun ConnectTruck(modifier: Modifier = Modifier) {
    var blurReady by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .size(size = 330.dp)
            .clip(shape = RoundedCornerShape(40.dp))
            .backgroundBlur(
                blurRadius = 9,
                shape = RoundedCornerShape(40.dp),
                backgroundColor = DarkGray,
                backgroundColorAlpha = 0.8f,
                onBlurReady = { blurReady = true }
            )
    ) {
        if (!blurReady) return

        Column(
            modifier = Modifier
                .padding(30.dp)
                .fillMaxWidth()
        ) {
            Text(
                "Overview",
                color = Color.White,
                fontSize = 18.sp,
                fontFamily = poppins(weight = FontWeight.Medium)
            )
            Text(
                "Nenhum caminhão conectado",
                color = LightGray,
                fontSize = 15.sp,
                fontFamily = poppins(weight = FontWeight.Light)
            )
            Button(
                onClick = { /*TODO*/ },
                shape = RoundedCornerShape(15.dp),
                colors = ButtonDefaults.buttonColors(containerColor = LightBlue),
                modifier = Modifier
                    .padding(vertical = 20.dp)
                    .size(width = 230.dp, height = 55.dp)
                    .border(width = 1.dp, color = LightDarkBlue, shape = RoundedCornerShape(15.dp))
                    .align(alignment = Alignment.CenterHorizontally)
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(7.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = "Conectar".uppercase(),
                        color = Color.Black,
                        fontFamily = poppins(weight = FontWeight.SemiBold),
                        fontSize = 18.sp
                    )
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                        contentDescription = "Arrow right icon",
                        tint = Color.Black
                    )
                }
            }
            Text(
                text = "Sobre o caminhão",
                fontSize = 18.sp,
                color = Color.White,
                fontFamily = poppins(weight = FontWeight.Medium)
            )
            Row(
                Modifier
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
                    ),
            ) {
                Row(
                    modifier = Modifier
                        .padding(vertical = 19.dp, horizontal = 17.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Box(
                            modifier = Modifier
                                .size(53.dp)
                                .clip(shape = RoundedCornerShape(size = 15.dp))
                                .background(color = LightDarkBlue),
                            contentAlignment = Alignment.Center
                        ) {
                            Image(
                                painter = painterResource(R.drawable.icon_truck),
                                contentDescription = "Truck icon",
                                modifier = Modifier.size(33.dp)
                            )
                        }
                        Column(
                            modifier = Modifier
                                .padding(start = 14.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "Cor",
                                color = Color.White,
                                fontSize = 17.sp,
                                fontFamily = poppins(weight = FontWeight.Medium)
                            )
                            Text(
                                text = "-/-",
                                color = LightGray,
                                fontSize = 15.sp,
                                fontFamily = poppins(weight = FontWeight.Medium)
                            )
                        }
                    }
                    VerticalDivider(
                        color = Gray,
                        modifier = Modifier.padding(vertical = 5.dp)
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.End
                    ) {
                        Box(
                            modifier = Modifier
                                .size(53.dp)
                                .clip(shape = RoundedCornerShape(size = 15.dp))
                                .background(color = LightDarkBlue),
                            contentAlignment = Alignment.Center
                        ) {
                            Image(
                                painter = painterResource(R.drawable.icon_truck_sign),
                                contentDescription = "Truck sign icon",
                                modifier = Modifier.size(33.dp)
                            )
                        }
                        Column(
                            modifier = Modifier
                                .padding(start = 14.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "Placa",
                                color = Color.White,
                                fontSize = 17.sp,
                                fontFamily = poppins(weight = FontWeight.Medium)
                            )
                            Text(
                                text = "-/-",
                                color = LightGray,
                                fontSize = 15.sp,
                                fontFamily = poppins(weight = FontWeight.Medium)
                            )
                        }
                    }
                }
            }
        }
    }
}


@Preview
@Composable
private fun ConnectTruckPreview() {
    ModTruckTheme {
        Surface(
            color = DarkGray
        ) {
            ConnectTruck()
        }
    }
}