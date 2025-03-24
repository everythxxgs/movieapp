package com.leonardbauer.movieapp.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun BottomNavBar(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val items = listOf(
        BottomNavItem(
            name = "Home",
            route = NavigationDestinations.HOME,
            icon = Icons.Default.Home
        ),
        BottomNavItem(
            name = "Search",
            route = NavigationDestinations.SEARCH,
            icon = Icons.Default.Search
        )
    )

    NavigationBar(modifier = modifier) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        items.forEach { item ->
            // Don't show bottom nav while in detail screen
            if (!currentRoute.isNullOrEmpty() && 
                !currentRoute.startsWith(NavigationDestinations.DETAILS.substringBefore("{"))
            ) {
                NavigationBarItem(
                    icon = { Icon(item.icon, contentDescription = item.name) },
                    label = { Text(text = item.name) },
                    selected = currentRoute == item.route,
                    onClick = {
                        if (currentRoute != item.route) {
                            navController.navigate(item.route) {
                                // Pop up to the start destination of the graph to
                                // avoid building up a large stack of destinations
                                popUpTo(NavigationDestinations.HOME) {
                                    saveState = true
                                }
                                // Avoid multiple copies of the same destination when
                                // reselecting the same item
                                launchSingleTop = true
                                // Restore state when reselecting a previously selected item
                                restoreState = true
                            }
                        }
                    }
                )
            }
        }
    }
}