package com.jpnacaratti.modtruck.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jpnacaratti.modtruck.ui.animations.rememberBaseMoveAnimation
import com.jpnacaratti.modtruck.ui.theme.Black
import com.jpnacaratti.modtruck.ui.theme.Gray
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

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color = LightBlue)
    ) {

        if (truckConnected.value) {
            val overviewAnimationState = rememberBaseMoveAnimation(
                duration = 1000,
                initialX = containerPosition.plus(150.dp.value),
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
                    .offset(y = 134.dp, x = overviewAnimationState.compOffsetX.dp)
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
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 25.dp),
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
                Column(
                    modifier = Modifier
                        .align(alignment = Alignment.Start)
                        .padding(top = 35.dp, start = 40.dp)
                ) {
                    Text(
                        text = "${truckViewModel.getConnectedModulesCount()}",
                        fontFamily = poppins(FontWeight.SemiBold),
                        fontSize = 54.sp,
                        color = Black
                    )
                    Text(
                        text = "Módulos",
                        fontFamily = poppins(FontWeight.Medium),
                        fontSize = 17.sp,
                        color = Black,
                        modifier = Modifier.offset(y = (-12).dp)
                    )
                    Text(
                        text = "Acoplados",
                        fontFamily = poppins(FontWeight.Normal),
                        fontSize = 16.sp,
                        color = Gray,
                        modifier = Modifier.offset(y = (-14).dp)
                    )
                }


            }
        }
    }
}

@Preview
@Composable
private fun ModulesScreenPreview() {
    GoogleFontProvider.initialize()

    ModTruckTheme {
        ModulesScreen(truckViewModel = TruckViewModel())
    }
}