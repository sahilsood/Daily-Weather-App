package xyz.sahilsood.dailyweather.repository

import android.util.Log
import kotlinx.coroutines.flow.Flow
import xyz.sahilsood.dailyweather.data.DataOrException
import xyz.sahilsood.dailyweather.data.WeatherDao
import xyz.sahilsood.dailyweather.models.DailyWeather
import xyz.sahilsood.dailyweather.models.Favorite
import xyz.sahilsood.dailyweather.network.WeatherApi
import javax.inject.Inject

class WeatherRepository @Inject constructor(
    private val weatherApi: WeatherApi,
    private val weatherDao: WeatherDao
) {

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
            Log.d("WeatherRepository", "Data: ${e.cause}")
            return DataOrException(e = e)
        }
        return DataOrException(data = response)
    }

    fun getFavorites(): Flow<List<Favorite>> = weatherDao.getFavorites()

    suspend fun getFavorite(city: String): Favorite = weatherDao.getFavorite(city)

    suspend fun insertFavorite(favorite: Favorite) = weatherDao.insertFavorite(favorite)

    suspend fun updateFavorite(favorite: Favorite) = weatherDao.updateFavorite(favorite)

    suspend fun deleteAllFavorites() = weatherDao.deleteAllFavorites()

    suspend fun deleteFavorites(favorite: Favorite) = weatherDao.deleteFavorites(favorite)

}