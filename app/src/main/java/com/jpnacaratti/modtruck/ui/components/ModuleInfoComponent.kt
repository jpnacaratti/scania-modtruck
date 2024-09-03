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
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jpnacaratti.modtruck.ui.theme.LightDarkBlue
import com.jpnacaratti.modtruck.ui.theme.LightGray
import com.jpnacaratti.modtruck.ui.theme.ModTruckTheme
import com.jpnacaratti.modtruck.ui.theme.White
import com.jpnacaratti.modtruck.utils.GoogleFontProvider
import com.jpnacaratti.modtruck.utils.GoogleFontProvider.Companion.poppins
import com.nacaratti.modtruck.R

@Composable
fun ModuleInfoComponent(icon: Painter, title: String, value: String, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier,
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
                painter = icon,
                contentDescription = "$title icon",
                modifier = Modifier.size(33.dp)
            )
        }
        Column(
            modifier = Modifier.padding(start = 8.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = title,
                color = White,
                fontSize = 17.sp,
                fontFamily = poppins(weight = FontWeight.Medium)
            )
            Text(
                text = value,
                color = LightGray,
                fontFamily = poppins(weight = FontWeight.Light),
            )
        }
    }
}

@Preview
@Composable
private fun ModuleInfoComponentPreview() {
    GoogleFontProvider.initialize()

    ModTruckTheme {
        ModuleInfoComponent(
            icon = painterResource(id = R.drawable.battery_level_module),
            title = "Bateria",
            value = "75%"
        )
    }
}