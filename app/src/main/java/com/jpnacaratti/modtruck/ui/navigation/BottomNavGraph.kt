package com.jpnacaratti.modtruck.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.jpnacaratti.modtruck.ui.screens.HomeScreen
import com.jpnacaratti.modtruck.ui.screens.ModulesScreen
import com.jpnacaratti.modtruck.ui.viewmodels.HomeScreenViewModel
import com.jpnacaratti.modtruck.ui.viewmodels.TruckViewModel

@Composable
fun BottomNavGraph(navController: NavHostController, truckViewModel: TruckViewModel, homeScreenViewModel: HomeScreenViewModel) {
    NavHost(
        navController = navController,
        startDestination = BottomBarScreen.Home.route
    ) {
        composable(route = BottomBarScreen.Profile.route) {
            // TODO: Implement
        }

        composable(route = BottomBarScreen.Home.route) {
            HomeScreen(truckViewModel = truckViewModel, screenViewModel = homeScreenViewModel)
        }

        composable(route = BottomBarScreen.Modules.route) {
            ModulesScreen(truckViewModel = truckViewModel)
        }
    }
}