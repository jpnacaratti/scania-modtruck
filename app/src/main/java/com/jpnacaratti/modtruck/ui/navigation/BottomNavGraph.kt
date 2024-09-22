package com.jpnacaratti.modtruck.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.jpnacaratti.modtruck.ui.screens.HomeScreen
import com.jpnacaratti.modtruck.ui.screens.ModulesScreen
import com.jpnacaratti.modtruck.ui.screens.ProfileScreen
import com.jpnacaratti.modtruck.ui.viewmodels.HomeScreenViewModel
import com.jpnacaratti.modtruck.ui.viewmodels.TruckViewModel
import com.jpnacaratti.modtruck.ui.viewmodels.UserViewModel

@Composable
fun BottomNavGraph(navController: NavHostController, truckViewModel: TruckViewModel, userViewModel: UserViewModel, homeScreenViewModel: HomeScreenViewModel) {
    NavHost(
        navController = navController,
        startDestination = BottomBarScreen.Home.route
    ) {
        composable(route = BottomBarScreen.Profile.route) {
            ProfileScreen(truckViewModel = truckViewModel, userViewModel = userViewModel)
        }

        composable(route = BottomBarScreen.Home.route) {
            HomeScreen(onNavigateToModules = {
                navController.navigate(BottomBarScreen.Modules.route) {
                    popUpTo(navController.graph.startDestinationId) {
                        saveState = true
                    }
                    restoreState = true
                    launchSingleTop = true
                }
            }, truckViewModel = truckViewModel, screenViewModel = homeScreenViewModel)
        }

        composable(route = BottomBarScreen.Modules.route) {
            ModulesScreen(truckViewModel = truckViewModel)
        }
    }
}