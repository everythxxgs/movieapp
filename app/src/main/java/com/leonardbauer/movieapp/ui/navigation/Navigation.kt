package com.leonardbauer.movieapp.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.leonardbauer.movieapp.data.repository.MovieRepository
import com.leonardbauer.movieapp.di.AppModule
import com.leonardbauer.movieapp.ui.details.DetailsScreen
import com.leonardbauer.movieapp.ui.details.DetailsViewModel
import com.leonardbauer.movieapp.ui.home.HomeScreen
import com.leonardbauer.movieapp.ui.home.HomeViewModel
import com.leonardbauer.movieapp.ui.search.SearchScreen
import com.leonardbauer.movieapp.ui.search.SearchViewModel

object NavigationDestinations {
    const val HOME = "home"
    const val SEARCH = "search"
    const val DETAILS = "details/{movieId}"
    
    fun detailsRoute(movieId: String) = "details/$movieId"
}

@Composable
fun MovieNavigation() {
    val navController = rememberNavController()
    
    // Initialize repository
    val client = remember { AppModule.provideOkHttpClient() }
    val apiService = remember { AppModule.provideApiService(client) }
    val repository = remember { AppModule.provideMovieRepository(apiService) }
    
    Scaffold(
        bottomBar = {
            BottomNavBar(navController = navController)
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = NavigationDestinations.HOME,
            modifier = Modifier.padding(innerPadding)
        ) {
        composable(NavigationDestinations.HOME) {
            val viewModel = remember { HomeViewModel(repository) }
            HomeScreen(
                viewModel = viewModel,
                onNavigateToSearch = { navController.navigate(NavigationDestinations.SEARCH) },
                onNavigateToDetails = { movieId ->
                    navController.navigate(NavigationDestinations.detailsRoute(movieId))
                }
            )
        }
        
        composable(NavigationDestinations.SEARCH) {
            val viewModel = remember { SearchViewModel(repository) }
            SearchScreen(
                viewModel = viewModel,
                onNavigateToDetails = { movieId ->
                    navController.navigate(NavigationDestinations.detailsRoute(movieId))
                },
                onNavigateBack = { navController.popBackStack() }
            )
        }
        
        composable(
            route = NavigationDestinations.DETAILS,
            arguments = listOf(navArgument("movieId") { type = NavType.StringType })
        ) { backStackEntry ->
            val movieId = backStackEntry.arguments?.getString("movieId") ?: return@composable
            val viewModel = remember { DetailsViewModel(repository) }
            DetailsScreen(
                viewModel = viewModel,
                movieId = movieId,
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
    }
}