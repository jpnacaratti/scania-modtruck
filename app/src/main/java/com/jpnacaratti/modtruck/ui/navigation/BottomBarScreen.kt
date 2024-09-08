package com.jpnacaratti.modtruck.ui.navigation

import com.nacaratti.modtruck.R

sealed class BottomBarScreen(
    val route: String,
    val iconSelected: Int,
    val iconUnselected: Int
) {
    data object Profile: BottomBarScreen(
        route = "profile",
        iconSelected = R.drawable.profile_filled,
        iconUnselected = R.drawable.profile_outlined
    )

    data object Home: BottomBarScreen(
        route = "home",
        iconSelected = R.drawable.home_filled,
        iconUnselected = R.drawable.home_outlined
    )

    data object Modules: BottomBarScreen(
        route = "modules",
        iconSelected = R.drawable.clipboard_filled,
        iconUnselected = R.drawable.clipboard_outlined
    )
}