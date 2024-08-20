package xyz.sahilsood.dailyweather.screens.main

import android.util.Log
import android.view.View
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import xyz.sahilsood.dailyweather.data.DataOrException
import xyz.sahilsood.dailyweather.models.DailyWeather
import xyz.sahilsood.dailyweather.repository.WeatherRepository
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: WeatherRepository) : ViewModel() {
    suspend fun getWeatherData(city: String): DataOrException<DailyWeather, Boolean, Exception> {
        return repository.getWeatherData(city, "metric")
    }
}