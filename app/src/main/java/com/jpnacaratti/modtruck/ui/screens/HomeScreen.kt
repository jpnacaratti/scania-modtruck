package com.jpnacaratti.modtruck.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.jpnacaratti.modtruck.ui.components.TruckInfoCard
import com.jpnacaratti.modtruck.ui.theme.DarkGray
import com.jpnacaratti.modtruck.ui.theme.ModTruckTheme
import com.jpnacaratti.modtruck.ui.viewmodels.AppUiState
import com.jpnacaratti.modtruck.ui.viewmodels.HomeScreenViewModel
import com.jpnacaratti.modtruck.utils.GoogleFontProvider
import com.nacaratti.modtruck.R

@Composable
fun HomeScreen(viewModel: HomeScreenViewModel, modifier: Modifier = Modifier) {
    val uiState by viewModel.uiState.collectAsState()
    HomeScreen(modifier =  modifier, state = uiState)
}

@Composable
fun HomeScreen(modifier: Modifier = Modifier, state: AppUiState = AppUiState()) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp

    val systemUiController = rememberSystemUiController()
    systemUiController.setSystemBarsColor(
        color = Color.Transparent,
        darkIcons = false
    )

    Box(
        modifier = modifier
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

        TruckInfoCard(state = state, modifier = Modifier
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
    val state = AppUiState(isBlurReady = true)

    GoogleFontProvider.initialize()

    ModTruckTheme {
        HomeScreen(state = state)
    }
}
