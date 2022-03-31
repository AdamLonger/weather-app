package com.firethings.something.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.firethings.something.data.local.model.WeatherConditionEntity
import com.firethings.something.data.local.model.WeatherEntity
import com.firethings.something.data.local.model.WeatherWithConditions
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {
    @Query("SELECT * FROM weather_table")
    fun weatherFlow(): Flow<List<WeatherWithConditions>>

    @Query("SELECT * FROM weather_table WHERE id = :id")
    suspend fun weatherById(id: Int): WeatherWithConditions?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEntry(entry: WeatherEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertConditionEntry(entry: WeatherConditionEntity)

    @Delete
    suspend fun deleteEntry(entry: WeatherEntity)
}
