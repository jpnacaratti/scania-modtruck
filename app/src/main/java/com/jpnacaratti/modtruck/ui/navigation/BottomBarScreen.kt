package com.jpnacaratti.modtruck.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomBarScreen(
    val route: String,
    val icon: ImageVector
) {
    data object Profile: BottomBarScreen(
        route = "profile",
        icon = Icons.Default.Person
    )

    data object Home: BottomBarScreen(
        route = "home",
        icon = Icons.Default.Home
    )

    data object Modules: BottomBarScreen(
        route = "modules",
        icon = Icons.AutoMirrored.Filled.List
    )
}