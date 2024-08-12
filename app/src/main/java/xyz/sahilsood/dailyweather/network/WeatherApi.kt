package xyz.sahilsood.dailyweather.network

import retrofit2.http.GET
import retrofit2.http.Query
import xyz.sahilsood.dailyweather.BuildConfig
import xyz.sahilsood.dailyweather.models.DailyWeather
import xyz.sahilsood.dailyweather.utils.Constants
import javax.inject.Singleton

@Singleton
interface WeatherApi {
    @GET(value = "data/2.5/forecast")
    suspend fun getWeatherData(
        @Query("q") q: String,
        @Query("units") units: String,
        @Query("appid") appid: String = BuildConfig.API_KEY
    ): DailyWeather
}