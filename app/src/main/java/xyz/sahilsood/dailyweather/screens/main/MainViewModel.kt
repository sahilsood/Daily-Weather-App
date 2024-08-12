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
    val data: MutableState<DataOrException<DailyWeather, Boolean, Exception>> =
        mutableStateOf(DataOrException(data = null, loading = true, Exception("")))

    init {
        getWeatherData(query = "Delhi", units = "metric")
    }

    private fun getWeatherData(query: String, units: String) {
        viewModelScope.launch {
            if (query.isEmpty()) return@launch
            data.value.loading = true
            data.value = repository.getWeatherData(query, units)
            if (data.value.data.toString().isNotEmpty()) data.value.loading = false
        }
        Log.d("MainViewModel", "Data: ${data.value.data}")
    }
}