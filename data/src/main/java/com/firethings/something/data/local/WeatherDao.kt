package com.firethings.something.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.firethings.something.data.local.model.WeatherConditionEntry
import com.firethings.something.data.local.model.WeatherEntry
import com.firethings.something.data.local.model.WeatherWithConditions
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {
    @Query("SELECT * FROM weather_table")
    fun weatherFlow(): Flow<List<WeatherWithConditions>>

    @Query("SELECT * FROM weather_table WHERE id = :id")
    suspend fun weatherById(id: Int): WeatherWithConditions?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEntry(entry: WeatherEntry): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertConditionEntry(entry: WeatherConditionEntry): Long

    @Transaction
    suspend fun insertEntryWithConditions(entry: WeatherEntry, conditions: List<WeatherConditionEntry>) {
        val parentId = insertEntry(entry)
        conditions.forEach { condition ->
            insertConditionEntry(condition.copy(parentId = parentId.toInt()))
        }
    }

    @Query("DELETE FROM weather_table WHERE id = :id")
    suspend fun deleteWeatherById(id: Int)

    @Query("DELETE FROM WEATHER_CONDITION_TABLE WHERE parent_id = :id")
    suspend fun deleteWeatherByParentId(id: Int)

    @Transaction
    suspend fun deleteWeatherAndConditionsById(id: Int) {
        deleteWeatherByParentId(id)
        deleteWeatherById(id)
    }
}
