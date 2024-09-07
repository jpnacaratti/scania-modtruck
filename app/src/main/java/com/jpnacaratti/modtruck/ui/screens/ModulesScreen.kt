package com.jpnacaratti.modtruck.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jpnacaratti.modtruck.ui.animations.rememberBaseAlphaAnimation
import com.jpnacaratti.modtruck.ui.animations.rememberBaseMoveAnimation
import com.jpnacaratti.modtruck.ui.components.ModulesOverview
import com.jpnacaratti.modtruck.ui.components.ModulesStatusInfo
import com.jpnacaratti.modtruck.ui.theme.Black
import com.jpnacaratti.modtruck.ui.theme.LightBlue
import com.jpnacaratti.modtruck.ui.theme.ModTruckTheme
import com.jpnacaratti.modtruck.ui.viewmodels.TruckViewModel
import com.jpnacaratti.modtruck.utils.GifImage
import com.jpnacaratti.modtruck.utils.GoogleFontProvider
import com.jpnacaratti.modtruck.utils.GoogleFontProvider.Companion.poppins
import com.nacaratti.modtruck.R

@Composable
fun ModulesScreen(truckViewModel: TruckViewModel, modifier: Modifier = Modifier) {
    val truckConnected = truckViewModel.truckConnected.collectAsState()

    var containerPosition by remember { mutableStateOf(0f) }

    val initialDelayAlpha = 300L
    val animationDurationAlpha = 225

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color = LightBlue)
            .verticalScroll(state = rememberScrollState())
    ) {

        if (truckConnected.value) {
            val containerAnimationState = rememberBaseMoveAnimation(
                duration = 1000,
                initialX = containerPosition.plus(220.dp.value),
                finalX = containerPosition
            )

            Image(
                painter = painterResource(R.drawable.container_behind),
                contentDescription = "Container behind the screen",
                modifier = Modifier
                    .padding(end = 40.dp)
                    .statusBarsPadding()
                    .height(426.dp)
                    .align(Alignment.TopEnd)
                    .offset(y = 134.dp, x = containerAnimationState.compOffsetX.dp)
                    .onGloballyPositioned { coordinates ->
                        containerPosition = coordinates.positionInParent().x
                    }
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val titleAnimationState = rememberBaseAlphaAnimation(
                duration = animationDurationAlpha,
                delayStart = initialDelayAlpha
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 25.dp)
                    .alpha(alpha = titleAnimationState.alpha),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Gerencie seus",
                    fontFamily = poppins(FontWeight.Light),
                    fontSize = 24.sp,
                    color = Black
                )
                Text(
                    text = "Módulos",
                    fontFamily = poppins(FontWeight.SemiBold),
                    fontSize = 24.sp,
                    color = Black
                )
            }

            if (!truckConnected.value) {
                GifImage(
                    drawable = R.drawable.modules_not_connected,
                    modifier = Modifier
                        .padding(bottom = 55.dp, top = 43.dp)
                        .size(size = 300.dp)
                )

                Text(
                    text = "Nenhum caminhão conectado",
                    fontFamily = poppins(FontWeight.Normal),
                    fontSize = 18.sp,
                    color = Black,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.width(width = 247.dp)
                )

                // TODO: Implement a button to see all available modules

            } else {
                ModulesOverview(
                    animationDurationAlpha = animationDurationAlpha,
                    initialDelayAlpha = initialDelayAlpha,
                    connectedModulesCount = truckViewModel.getConnectedModulesCount(),
                    modulesStatusIcon = truckViewModel.getAllModulesStatusIcon(),
                    modulesStatusDescription = truckViewModel.getAllModulesStatusDescription(),
                    modifier = Modifier.align(alignment = Alignment.Start)
                )

                Spacer(modifier = Modifier.height(height = 35.dp))

                ModulesStatusInfo(
                    modules = truckViewModel.getAllModulesDetails()
                )

                Spacer(modifier = Modifier.height(height = 100.dp))
            }
        }
    }
}

@Preview
@Composable
private fun ModulesScreenPreview() {
    val truckViewModel = TruckViewModel()
    truckViewModel.setTruckConnected(true)

    GoogleFontProvider.initialize()

    ModTruckTheme {
        ModulesScreen(truckViewModel = truckViewModel)
    }
}