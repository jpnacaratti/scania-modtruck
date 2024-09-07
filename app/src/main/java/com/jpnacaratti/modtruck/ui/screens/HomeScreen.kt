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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jpnacaratti.modtruck.models.TruckInfo
import com.jpnacaratti.modtruck.ui.animations.rememberBaseMoveAnimation
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
    val truckConnected by truckViewModel.truckConnected.collectAsState()

    var overviewCardPosition by remember { mutableStateOf(0f) }
    var infoCardPosition by remember { mutableStateOf(0f) }
    var modulesCardPosition by remember { mutableStateOf(0f) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.primary)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box {
            Image(
                painter = painterResource(R.drawable.truck_not_connected),
                contentDescription = "Truck base image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(439.dp)
                    .offset(x = (-10).dp)
            )

            if (truckConnected) {
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

        val overviewAnimationState = rememberBaseMoveAnimation(
            duration = 750,
            initialY = overviewCardPosition.plus(500.dp.value),
            finalY = overviewCardPosition.minus(21.dp.value)
        )

        TruckOverviewCard(
            truckConnected = truckConnected,
            state = state,
            truckInfo = truckViewModel.truckInfo.value,
            modifier = Modifier
                .offset(y = overviewAnimationState.compOffsetY.dp)
                .onGloballyPositioned { coordinates ->
                    overviewCardPosition = coordinates.positionInParent().y
                }
        )

        if (!truckConnected) return

        val infoAnimationState = rememberBaseMoveAnimation(
            duration = 750,
            initialY = infoCardPosition.plus(500.dp.value),
            finalY = infoCardPosition,
            delayStart = 375
        )

        TruckInfoCard(
            truckViewModel = truckViewModel,
            modifier = Modifier
                .offset(y = infoAnimationState.compOffsetY.dp)
                .onGloballyPositioned { coordinates ->
                    infoCardPosition = coordinates.positionInParent().y
                }
        )

        val modulesAnimationState = rememberBaseMoveAnimation(
            duration = 750,
            initialY = modulesCardPosition.plus(500.dp.value),
            finalY = modulesCardPosition,
            delayStart = 750
        )

        TruckViewModulesCard(
            modifier = Modifier
                .padding(top = 20.dp, bottom = 100.dp)
                .offset(y = modulesAnimationState.compOffsetY.dp)
                .onGloballyPositioned { coordinates ->
                    modulesCardPosition = coordinates.positionInParent().y
                }
        )
    }
}

@Preview
@Composable
private fun HomeScreenPreview() {
    val state = HomeScreenUiState(
        isFirstCardBlurReady = true
    )

    val viewModel = TruckViewModel()

    GoogleFontProvider.initialize()

    ModTruckTheme {
        HomeScreen(truckViewModel = viewModel, state = state)
    }
}
