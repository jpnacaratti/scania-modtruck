package com.jpnacaratti.modtruck.ui.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import com.jpnacaratti.modtruck.ui.theme.DarkBlue
import com.jpnacaratti.modtruck.ui.theme.Gray
import com.jpnacaratti.modtruck.ui.theme.LightDarkBlue
import com.jpnacaratti.modtruck.ui.theme.ModTruckTheme
import com.jpnacaratti.modtruck.ui.theme.White
import com.jpnacaratti.modtruck.utils.GoogleFontProvider

@Composable
fun BottomBar(navController: NavController) {
    val screens = listOf(
        BottomBarScreen.Profile,
        BottomBarScreen.Home,
        BottomBarScreen.Modules
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    Box(modifier = Modifier.fillMaxWidth().height(120.dp)) {
        Box(
            modifier = Modifier
                .clip(shape = RoundedCornerShape(size = 35.dp))
                .height(60.dp)
                .width(210.dp)
                .background(color = LightDarkBlue)
                .align(alignment = Alignment.Center)
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                screens.forEach { screen ->

                    val selected =
                        currentDestination?.hierarchy?.any { it.route == screen.route } == true

                    Box(
                        modifier = Modifier
                            .clip(shape = CircleShape)
                            .fillMaxHeight()
                            .width(if (selected) 75.dp else 60.dp)
                            .background(color = if (selected) DarkBlue else Color.Transparent)
                            .clickable {
                                navController.navigate(screen.route) {
                                    popUpTo(navController.graph.startDestinationId) {
                                        saveState = true
                                    }
                                    restoreState = true
                                    launchSingleTop = true
                                }
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = screen.icon,
                            tint = if (selected) White else Gray,
                            contentDescription = "Navigation icon",
                            modifier = Modifier.size(25.dp)
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun BottomBarPreview() {

    GoogleFontProvider.initialize()

    ModTruckTheme {
        BottomBar(navController = NavController(LocalContext.current))
    }
}