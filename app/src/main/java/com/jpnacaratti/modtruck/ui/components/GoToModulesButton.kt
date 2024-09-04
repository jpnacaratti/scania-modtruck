package com.jpnacaratti.modtruck.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jpnacaratti.modtruck.ui.theme.Black
import com.jpnacaratti.modtruck.ui.theme.ModTruckTheme
import com.jpnacaratti.modtruck.ui.theme.White
import com.jpnacaratti.modtruck.utils.GoogleFontProvider
import com.jpnacaratti.modtruck.utils.GoogleFontProvider.Companion.poppins

@Composable
fun GoToModulesButton(modifier: Modifier = Modifier, onClickListener: () -> Unit = {}) {
    Button(
        onClick = onClickListener,
        shape = RoundedCornerShape(15.dp),
        colors = ButtonDefaults.buttonColors(containerColor = White),
        modifier = modifier
            .size(width = 142.dp, height = 51.dp)
            .border(
                width = 1.dp,
                color = Black,
                shape = RoundedCornerShape(15.dp)
            )
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(1.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Detalhes",
                color = Color.Black,
                fontSize = 16.sp,
                fontFamily = poppins(weight = FontWeight.Light)
            )
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = "Arrow right icon",
                tint = Color.Black
            )
        }
    }
}

@Preview
@Composable
private fun GoToModulesButtonPreview() {
    GoogleFontProvider.initialize()

    ModTruckTheme {
        GoToModulesButton()
    }
}