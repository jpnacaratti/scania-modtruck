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
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.layout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jpnacaratti.modtruck.ui.theme.Gray
import com.jpnacaratti.modtruck.ui.theme.LightDarkBlue
import com.jpnacaratti.modtruck.ui.theme.LightGray
import com.jpnacaratti.modtruck.ui.theme.White
import com.jpnacaratti.modtruck.utils.GoogleFontProvider.Companion.poppins
import com.nacaratti.modtruck.R

@Composable
fun AboutTruckSection(truckColor: String, truckSign: String, modifier: Modifier = Modifier) {
    Row(
        modifier
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
                        color = White,
                        fontSize = 17.sp,
                        fontFamily = poppins(weight = FontWeight.Medium)
                    )
                    Text(
                        text = truckColor,
                        color = LightGray,
                        fontSize = 15.sp,
                        fontFamily = poppins(weight = FontWeight.Light)
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
                        color = White,
                        fontSize = 17.sp,
                        fontFamily = poppins(weight = FontWeight.Medium)
                    )
                    Text(
                        text = truckSign,
                        color = LightGray,
                        fontSize = 15.sp,
                        fontFamily = poppins(weight = FontWeight.Light)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun AboutTruckSectionPreview() {
    AboutTruckSection(truckColor = "Azul", truckSign = "ABC-1234")
}