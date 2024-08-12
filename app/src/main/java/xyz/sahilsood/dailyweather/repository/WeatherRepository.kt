package xyz.sahilsood.dailyweather.repository

import android.util.Log
import xyz.sahilsood.dailyweather.data.DataOrException
import xyz.sahilsood.dailyweather.models.DailyWeather
import xyz.sahilsood.dailyweather.network.WeatherApi
import javax.inject.Inject

class WeatherRepository @Inject constructor(private val weatherApi: WeatherApi) {
    suspend fun getWeatherData(
        query: String,
        units: String
    ): DataOrException<DailyWeather, Boolean, Exception> {
        val response = try {
            val response = weatherApi.getWeatherData(q = query, units = units)
            Log.d("WeatherRepository", "Data: ${response.list}")
            response
        } catch (e: Exception) {
            Log.d("WeatherRepository", "Data: ${e.message}")
            return DataOrException(e = e)
        }
        return DataOrException(data = response)
    }
}