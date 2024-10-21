package xyz.sahilsood.dailyweather.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import xyz.sahilsood.dailyweather.models.Favorite

@Dao
interface WeatherDao {
    @Query("SELECT * FROM favorites")
    fun getFavorites(): Flow<List<Favorite>>

    @Query("SELECT * FROM favorites WHERE city = :city")
    suspend fun getFavorite(city: String): Favorite

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(favorite: Favorite)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateFavorite(favorite: Favorite)

    @Query("DELETE FROM favorites")
    suspend fun deleteAllFavorites()

    @Delete
    suspend fun deleteFavorites(favorite: Favorite)
}