package xyz.sahilsood.dailyweather.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import xyz.sahilsood.dailyweather.screens.main.MainScreen
import xyz.sahilsood.dailyweather.screens.SplashScreen


@Composable
fun WeatherNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = WeatherScreens.SplashScreen.name) {
        composable(WeatherScreens.SplashScreen.name) {
            SplashScreen(navController = navController)
        }
        composable(WeatherScreens.MainScreen.name) {

            MainScreen(navController = navController)
        }
    }
}