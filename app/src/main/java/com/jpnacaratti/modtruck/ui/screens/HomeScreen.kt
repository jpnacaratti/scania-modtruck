package com.jpnacaratti.modtruck.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.jpnacaratti.modtruck.ui.components.ConnectTruck
import com.jpnacaratti.modtruck.ui.theme.ModTruckTheme
import com.nacaratti.modtruck.R

@Composable
fun HomeScreen() {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp

    val systemUiController = rememberSystemUiController()
    systemUiController.setSystemBarsColor(
        color = Color.Transparent,
        darkIcons = false
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.primary)
    ) {
        Image(
            painter = painterResource(R.drawable.truck_not_connected),
            contentDescription = "Truck not connected image",
            modifier = Modifier
                .fillMaxWidth()
                .height(439.dp)
        )

        ConnectTruck(modifier = Modifier
            .offset(
                x = (screenWidth - 330.dp) / 2,
                y = 408.dp
            )
        )
    }
}

@Preview
@Composable
private fun HomeScreenPreview() {
    ModTruckTheme {
        HomeScreen()
    }
}
