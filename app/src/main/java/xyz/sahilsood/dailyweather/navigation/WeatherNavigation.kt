package xyz.sahilsood.dailyweather.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import xyz.sahilsood.dailyweather.screens.SplashScreen
import xyz.sahilsood.dailyweather.screens.about.AboutScreen
import xyz.sahilsood.dailyweather.screens.favorites.FavoritesScreen
import xyz.sahilsood.dailyweather.screens.main.MainScreen
import xyz.sahilsood.dailyweather.screens.search.SearchScreen


@Composable
fun WeatherNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = WeatherScreens.SplashScreen.name) {
        composable(WeatherScreens.SplashScreen.name) {
            SplashScreen(navController = navController)
        }
        val route = WeatherScreens.MainScreen.name
        composable(
            route = "$route/{city}",
            arguments = listOf(navArgument("city") { type = NavType.StringType })
        ) { navBack ->
            navBack.arguments?.getString("city")?.let { city ->
                MainScreen(navController = navController, city = city)
            }
        }
        composable(WeatherScreens.SearchScreen.name) {
            SearchScreen(navController = navController)
        }
        composable(WeatherScreens.AboutScreen.name) {
            AboutScreen(navController = navController)
        }
        composable(WeatherScreens.FavoritesScreen.name) {
            FavoritesScreen(navController = navController)
        }
    }
}