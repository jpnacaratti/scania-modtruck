package com.jpnacaratti.modtruck.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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
    Box(
        modifier = modifier
            .size(size = 330.dp)
            .clip(shape = RoundedCornerShape(40.dp))
            .backgroundBlur(
                blurRadius = 9,
                shape = RoundedCornerShape(40.dp),
                backgroundColor = Gray,
                backgroundColorAlpha = 0.8f
            )
    ) {
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
                    .border(width = 1.dp, color = DarkGray, shape = RoundedCornerShape(15.dp))
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
            Text(text = "Sobre o caminhão")
        }
    }
}


@Preview
@Composable
private fun ConnectTruckPreview() {
    ModTruckTheme {
        Surface(
            color = Gray
        ) {
            ConnectTruck()
        }
    }
}