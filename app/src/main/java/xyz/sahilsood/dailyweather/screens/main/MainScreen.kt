package xyz.sahilsood.dailyweather.screens.main

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import xyz.sahilsood.dailyweather.data.DataOrException
import xyz.sahilsood.dailyweather.models.DailyWeather
import xyz.sahilsood.dailyweather.widgets.WeatherAppBar

@Composable
fun MainScreen(navController: NavHostController, mainViewModel: MainViewModel = hiltViewModel()) {
    val weatherData = produceState<DataOrException<DailyWeather, Boolean, Exception>>(
        initialValue = DataOrException(loading = true)
    ) {
        value = mainViewModel.getWeatherData("London")
    }.value

    if (weatherData.loading == true) {
        CircularProgressIndicator()
    } else if (weatherData.data != null) {
        MainScaffold(weather = weatherData.data!!, navController = navController)
    }
}

@Composable
fun MainScaffold(weather: DailyWeather, navController: NavController) {
    Scaffold(topBar = {
        WeatherAppBar(
            title = weather.city.name + ", ${weather.city.country}",
            navController = navController,
            elevation = 5.dp
        )
    }) { paddingValues ->
        MainContent(data = weather, paddingValues = paddingValues)
    }
}

@Composable
fun MainContent(data: DailyWeather, paddingValues: PaddingValues) {
    Text(text = data.city.name)
}
