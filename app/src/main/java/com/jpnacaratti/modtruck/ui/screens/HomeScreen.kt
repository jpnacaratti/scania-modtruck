package com.jpnacaratti.modtruck.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.jpnacaratti.modtruck.models.TruckInfo
import com.jpnacaratti.modtruck.ui.animations.rememberTruckEntryAnimation
import com.jpnacaratti.modtruck.ui.components.TruckInfoCard
import com.jpnacaratti.modtruck.ui.components.TruckOverviewCard
import com.jpnacaratti.modtruck.ui.components.TruckViewModulesCard
import com.jpnacaratti.modtruck.ui.theme.ModTruckTheme
import com.jpnacaratti.modtruck.ui.states.HomeScreenUiState
import com.jpnacaratti.modtruck.ui.viewmodels.HomeScreenViewModel
import com.jpnacaratti.modtruck.ui.viewmodels.TruckViewModel
import com.jpnacaratti.modtruck.utils.GoogleFontProvider
import com.nacaratti.modtruck.R

@Composable
fun HomeScreen(truckViewModel: TruckViewModel, screenViewModel: HomeScreenViewModel, modifier: Modifier = Modifier) {
    val uiState by screenViewModel.uiState.collectAsState()
    HomeScreen(truckViewModel = truckViewModel, modifier = modifier, state = uiState)
}

@Composable
fun HomeScreen(truckViewModel: TruckViewModel, modifier: Modifier = Modifier, state: HomeScreenUiState = HomeScreenUiState()) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.primary)
            .verticalScroll(
                rememberScrollState()
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box {
            Image(
                painter = painterResource(R.drawable.truck_not_connected),
                contentDescription = "Truck base image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(height = 439.dp)
                    .offset(x = (-10).dp)
            )

            if (state.isTruckConnected) {

                val animationState = rememberTruckEntryAnimation(duration = 2000)

                Image(
                    painter = painterResource(R.drawable.truck_connected),
                    contentDescription = "Truck image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .offset(
                            x = animationState.truckOffsetX.dp,
                            y = animationState.truckOffsetY.dp
                        )
                        .height(439.dp)
                )
            }
        }

        TruckOverviewCard(
            state = state, modifier = Modifier
                .offset(
                    y = (-21).dp
                )
        )

        if (!state.isTruckConnected) return

        TruckInfoCard(truckViewModel = truckViewModel)

        TruckViewModulesCard(modifier = Modifier.padding(top = 20.dp, bottom = 100.dp))
    }
}

@Preview
@Composable
private fun HomeScreenPreview() {
    val state = HomeScreenUiState(
        isFirstCardBlurReady = true,
        isTruckConnected = true,
        isTruckInfo = TruckInfo(
            truckColor = "Laranja",
            truckSign = "ABC-1234",
            truckModel = "Scania 620S V8"
        )
    )

    val viewModel = TruckViewModel()

    GoogleFontProvider.initialize()

    ModTruckTheme {
        HomeScreen(truckViewModel = viewModel, state = state)
    }
}
