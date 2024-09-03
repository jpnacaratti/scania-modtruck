package com.jpnacaratti.modtruck.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jpnacaratti.modtruck.ui.states.HomeScreenUiState
import com.jpnacaratti.modtruck.ui.theme.DarkGray
import com.jpnacaratti.modtruck.ui.theme.LightBlue
import com.jpnacaratti.modtruck.ui.theme.LightGray
import com.jpnacaratti.modtruck.ui.theme.ModTruckTheme
import com.jpnacaratti.modtruck.ui.theme.White
import com.jpnacaratti.modtruck.utils.GoogleFontProvider
import com.jpnacaratti.modtruck.utils.GoogleFontProvider.Companion.poppins

@Composable
fun TruckInfoCard(state: HomeScreenUiState, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .width(330.dp)
            .height(431.dp)
            .clip(shape = RoundedCornerShape(40.dp))
            .background(color = DarkGray)
            .alpha(alpha = 0.8f)
            .padding(19.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Informações",
            color = White,
            fontFamily = poppins(weight = FontWeight.Medium),
            fontSize = 20.sp
        )
        Row(
            modifier = Modifier
                .padding(top = 14.dp, bottom = 22.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "5",
                color = LightBlue,
                fontFamily = poppins(weight = FontWeight.Medium),
                fontSize = 40.sp
            )
            Text(
                text = "módulos acoplados",
                color = LightGray,
                fontFamily = poppins(weight = FontWeight.Light),
                fontSize = 16.sp,
                modifier = Modifier
                    .width(93.dp)
                    .padding(start = 13.dp)
            )
        }
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            Text(
                text = "Tempo de atividade:",
                color = White,
                fontFamily = poppins(weight = FontWeight.Light),
                fontSize = 17.sp
            )
            Text(text = "1hr 32m 20s",
                color = LightGray,
                fontFamily = poppins(weight = FontWeight.Light),
                fontSize = 17.sp
            )
        }
    }
}

@Preview
@Composable
private fun TruckInfoCardPreview() {
    GoogleFontProvider.initialize()

    ModTruckTheme {
        TruckInfoCard(HomeScreenUiState())
    }
}