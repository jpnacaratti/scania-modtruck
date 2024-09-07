package com.jpnacaratti.modtruck.ui.activities

import android.content.Context
import android.content.IntentFilter
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.jpnacaratti.modtruck.bluetooth.BluetoothService.Companion.TRUCK_CONNECTED
import com.jpnacaratti.modtruck.bluetooth.BluetoothReceiver
import com.jpnacaratti.modtruck.bluetooth.BluetoothService.Companion.TRUCK_INFO_RECEIVED
import com.jpnacaratti.modtruck.ui.navigation.BottomBar
import com.jpnacaratti.modtruck.ui.navigation.BottomNavGraph
import com.jpnacaratti.modtruck.ui.theme.ModTruckTheme
import com.jpnacaratti.modtruck.ui.viewmodels.HomeScreenViewModel
import com.jpnacaratti.modtruck.ui.viewmodels.TruckViewModel
import com.jpnacaratti.modtruck.utils.GoogleFontProvider

class MainActivity : ComponentActivity() {

    private lateinit var bluetoothReceiver: BluetoothReceiver
    private val truckViewModel by viewModels<TruckViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        registerBluetoothReceiver()

        GoogleFontProvider.initialize()

        enableEdgeToEdge()

        window.attributes.layoutInDisplayCutoutMode =
            WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES

        val screenViewModel by viewModels<HomeScreenViewModel>()

        setContent {
            ModTruck(
                truckViewModel = truckViewModel,
                homeScreenViewModel = screenViewModel
            )
        }
    }

    private fun registerBluetoothReceiver() {
        bluetoothReceiver = BluetoothReceiver(truckViewModel)

        val intentFilter = IntentFilter().apply {
            addAction(TRUCK_CONNECTED)
            addAction(TRUCK_INFO_RECEIVED)
        }

        registerReceiver(bluetoothReceiver, intentFilter, Context.RECEIVER_NOT_EXPORTED)
    }
}

@Composable
fun ModTruck(truckViewModel: TruckViewModel, homeScreenViewModel: HomeScreenViewModel) {
    val navController = rememberNavController()

    ModTruckTheme {
        Scaffold(
            bottomBar = {
                BottomBar(navController = navController)
            }
        ) { innerPadding ->
            Box(
                modifier = Modifier.padding(
                    PaddingValues(
                        start = innerPadding.calculateStartPadding(LocalLayoutDirection.current),
                        top = 0.dp,
                        end = innerPadding.calculateEndPadding(LocalLayoutDirection.current),
                        bottom = 0.dp
                    )
                )
            ) {
                val systemUiController = rememberSystemUiController()
                systemUiController.setSystemBarsColor(
                    color = Color.Transparent
                )

                BottomNavGraph(
                    navController = navController,
                    truckViewModel = truckViewModel,
                    homeScreenViewModel = homeScreenViewModel
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AppPreview() {
    ModTruck(truckViewModel = TruckViewModel(), homeScreenViewModel = HomeScreenViewModel())
}