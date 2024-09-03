package com.jpnacaratti.modtruck.ui.activities

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.jpnacaratti.modtruck.ui.screens.HomeScreen
import com.jpnacaratti.modtruck.ui.theme.ModTruckTheme
import com.jpnacaratti.modtruck.ui.viewmodels.HomeScreenViewModel
import com.jpnacaratti.modtruck.ui.viewmodels.TruckViewModel
import com.jpnacaratti.modtruck.utils.GoogleFontProvider

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        GoogleFontProvider.initialize()

        enableEdgeToEdge()

        window.attributes.layoutInDisplayCutoutMode =
            WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES

        val truckViewModel by viewModels<TruckViewModel>()

        setContent {
            val screenViewModel by viewModels<HomeScreenViewModel>()
            App(
                truckViewModel = truckViewModel,
                homeScreenViewModel = screenViewModel
            )
        }
    }
}

@Composable
fun App(truckViewModel: TruckViewModel, homeScreenViewModel: HomeScreenViewModel) {
    ModTruckTheme {
        HomeScreen(
            truckViewModel = truckViewModel,
            screenViewModel = homeScreenViewModel
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AppPreview() {
//    App(homeScreenViewModel = HomeScreenViewModel())
}