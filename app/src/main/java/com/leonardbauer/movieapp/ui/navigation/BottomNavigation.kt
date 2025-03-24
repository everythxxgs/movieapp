package com.leonardbauer.movieapp.ui.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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

    NavigationBar(
        modifier = modifier
            .fillMaxWidth()
            .height(76.dp)
            .padding(bottom = 0.dp)
            .shadow(elevation = 8.dp, spotColor = Color(0x1A000000)),
        containerColor = Color.White,
        tonalElevation = 0.dp
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        val showBottomNav = currentRoute != null &&
            !currentRoute.startsWith(NavigationDestinations.DETAILS.substringBefore("{"))
        
        if (showBottomNav) {
            items.forEach { item ->
                NavigationBarItem(
                    icon = { 
                        Icon(
                            item.icon, 
                            contentDescription = item.name,
                            modifier = Modifier.size(24.dp)
                        ) 
                    },
                    label = { 
                        Text(
                            text = item.name,
                            fontSize = 12.sp,
                            fontWeight = if (currentRoute == item.route) FontWeight.SemiBold else FontWeight.Normal
                        ) 
                    },
                    selected = currentRoute == item.route,
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MaterialTheme.colorScheme.primary,
                        selectedTextColor = MaterialTheme.colorScheme.primary,
                        unselectedIconColor = Color.Gray,
                        unselectedTextColor = Color.Gray,
                        indicatorColor = Color.Transparent // iOS doesn't use indicators
                    ),
                    onClick = {
                        if (currentRoute != item.route) {
                            navController.navigate(item.route) {

                                popUpTo(NavigationDestinations.HOME) {
                                    saveState = true
                                }

                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    }
                )
            }
        }
    }
}