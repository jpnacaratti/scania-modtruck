package com.jpnacaratti.modtruck.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jpnacaratti.modtruck.ui.theme.LightGray
import com.jpnacaratti.modtruck.ui.theme.ModTruckTheme
import com.jpnacaratti.modtruck.ui.theme.White
import com.jpnacaratti.modtruck.utils.GoogleFontProvider
import com.jpnacaratti.modtruck.utils.GoogleFontProvider.Companion.poppins

@Composable
fun ProfileDrivenInfo(drivenHours: Int, drivenYears: Long, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(modifier = Modifier, horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "+$drivenHours horas",
                color = White,
                fontFamily = poppins(FontWeight.Medium),
                fontSize = 16.sp
            )
            Text(
                text = "Pilotadas",
                color = LightGray,
                fontSize = 14.sp,
                fontFamily = poppins(FontWeight.Light)
            )
        }
        Column(modifier = Modifier, horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "$drivenYears Anos",
                color = White,
                fontFamily = poppins(FontWeight.Medium),
                fontSize = 16.sp
            )
            Text(
                text = "Experiência",
                color = LightGray,
                fontSize = 14.sp,
                fontFamily = poppins(FontWeight.Light)
            )
        }
    }

}

@Preview
@Composable
private fun ProfileDrivenInfoPreview() {
    GoogleFontProvider.initialize()

    ModTruckTheme {
        ProfileDrivenInfo(drivenHours = 550, drivenYears = 9)
    }
}