package com.jpnacaratti.modtruck.ui.navigation

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import com.jpnacaratti.modtruck.ui.theme.DarkBlue
import com.jpnacaratti.modtruck.ui.theme.Gray

@Composable
fun BottomBar(navController: NavController) {
    val screens = listOf(
        BottomBarScreen.Profile,
        BottomBarScreen.Home,
        BottomBarScreen.Modules
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    HorizontalDivider(color = Gray, modifier = Modifier.zIndex(2F).padding(horizontal = 40.dp))
    BottomAppBar(
        containerColor = DarkBlue,
        actions = {
            screens.forEach { screen ->
                NavigationBarItem(
                    icon = {
                        Icon(
                            imageVector = screen.icon,
                            contentDescription = "Navigation icon"
                        )
                    },
                    selected = currentDestination?.hierarchy?.any {
                        it.route == screen.route
                    } == true,
                    onClick = {
                        navController.navigate(screen.route)
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color.White,
                        unselectedIconColor = Color.Gray,
                        indicatorColor = Color.Transparent
                    )
                )
            }
        },
        modifier = Modifier.height(80.dp)
    )
}