package com.jpnacaratti.modtruck.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jpnacaratti.modtruck.ui.theme.DarkGray
import com.jpnacaratti.modtruck.ui.theme.LightGray
import com.jpnacaratti.modtruck.ui.theme.ModTruckTheme
import com.jpnacaratti.modtruck.ui.theme.White
import com.jpnacaratti.modtruck.utils.GoogleFontProvider
import com.jpnacaratti.modtruck.utils.GoogleFontProvider.Companion.poppins
import com.nacaratti.modtruck.R

@Composable
fun TruckViewModulesCard(modifier: Modifier = Modifier) {
    Row(modifier = modifier
        .width(330.dp)
        .height(224.dp)
        .clip(shape = RoundedCornerShape(40.dp))
        .background(color = DarkGray)
        .alpha(alpha = 0.8f)
        .padding(horizontal = 13.dp, vertical = 35.dp)
    ) {
        Image(
            painter = painterResource(R.drawable.truck_frontal),
            contentDescription = "Truck frontal image",
            modifier = Modifier
                .width(127.dp)
                .height(154.dp)
        )
        Column(
            modifier = Modifier
            .padding(start = 18.dp)
            .fillMaxHeight(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = "Módulos",
                    fontSize = 18.sp,
                    color = White,
                    fontFamily = poppins(weight = FontWeight.Medium)
                )
                Text(
                    text = "Gerencie os módulos acoplados",
                    fontSize = 13.sp,
                    color = LightGray,
                    fontFamily = poppins(FontWeight.Light)
                )
            }

            GoToModulesButton(onClickListener = { /* TODO: Navegar para tela de módulos */ })
        }
    }
}

@Preview
@Composable
private fun TruckViewModulesCardPreview() {
    GoogleFontProvider.initialize()

    ModTruckTheme {
        TruckViewModulesCard()
    }
}