package com.jpnacaratti.modtruck.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jpnacaratti.modtruck.ui.components.ConnectTruck
import com.jpnacaratti.modtruck.ui.theme.ModTruckTheme
import com.nacaratti.modtruck.R

@Composable
fun HomeScreen() {
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

        ConnectTruck()
    }
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Preview
@Composable
private fun HomeScreenPreview() {
    ModTruckTheme {
        HomeScreen()
    }
}
