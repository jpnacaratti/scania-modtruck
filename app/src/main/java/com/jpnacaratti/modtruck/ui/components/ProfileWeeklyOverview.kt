package com.jpnacaratti.modtruck.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jpnacaratti.modtruck.ui.theme.ModTruckTheme
import com.jpnacaratti.modtruck.ui.theme.White
import com.jpnacaratti.modtruck.utils.GoogleFontProvider
import com.jpnacaratti.modtruck.utils.GoogleFontProvider.Companion.poppins
import com.nacaratti.modtruck.R

@Composable
fun ProfileWeeklyOverview(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Text(
            text = "Relatório semanal",
            fontFamily = poppins(weight = FontWeight.Normal),
            fontSize = 17.sp,
            color = White,
            modifier = Modifier.padding(bottom = 15.dp)
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Image(
                painter = painterResource(id = R.drawable.hours_drove_graph),
                contentDescription = "Hours drove graph",
                modifier = Modifier.size(size = 160.dp)
            )

            Image(
                painter = painterResource(id = R.drawable.general_value_graph),
                contentDescription = "General value graph",
                modifier = Modifier.size(size = 160.dp)
            )
        }
    }
}

@Preview
@Composable
private fun ProfileWeeklyOverviewPreview() {
    GoogleFontProvider.initialize()

    ModTruckTheme {
        ProfileWeeklyOverview()
    }
}