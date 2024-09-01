package com.jpnacaratti.modtruck.ui.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.jpnacaratti.modtruck.ui.screens.HomeScreen
import com.jpnacaratti.modtruck.ui.theme.ModTruckTheme
import com.jpnacaratti.modtruck.ui.viewmodels.HomeScreenViewModel
import com.jpnacaratti.modtruck.utils.GoogleFontProvider

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        GoogleFontProvider.initialize()

        enableEdgeToEdge()
        setContent {
            val viewModel by viewModels<HomeScreenViewModel>()
            App(viewModel)
        }
    }
}

@Composable
fun App(viewModel: HomeScreenViewModel) {
    ModTruckTheme {
        HomeScreen(viewModel = viewModel)
    }
}

@Preview(showBackground = true)
@Composable
fun AppPreview() {
    App(viewModel = HomeScreenViewModel())
}