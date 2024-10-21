package xyz.sahilsood.dailyweather.data

import androidx.room.Database
import androidx.room.RoomDatabase
import xyz.sahilsood.dailyweather.models.Favorite

@Database(entities = [Favorite::class], version = 1, exportSchema = false)
abstract class WeatherDatabase: RoomDatabase() {
    abstract fun weatherDao(): WeatherDao
}