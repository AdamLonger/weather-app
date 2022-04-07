package com.firethings.something.data.local

import android.database.Cursor
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.firethings.something.data.local.model.WeatherEntry

@Dao
interface SimpleWeatherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertEntry(entry: WeatherEntry): Long

    @Update
    fun updateEntry(entry: WeatherEntry)

    @Query("DELETE FROM weather_table WHERE id = :id")
    fun deleteWeatherById(id: Long): Int

    @Query("DELETE FROM WEATHER_CONDITION_TABLE WHERE parent_id = :id")
    fun deleteWeatherConditionByParentId(id: Long)

    @Transaction
    fun deleteWeatherAndConditionsById(id: Long) {
        deleteWeatherConditionByParentId(id)
        deleteWeatherById(id)
    }

    @Query("SELECT * FROM weather_table")
    fun weatherCursor(): Cursor

    @Query("SELECT * FROM weather_table WHERE id = :id")
    fun weatherCursorById(id: Long): Cursor

    @Query("SELECT COUNT(id) FROM weather_table")
    fun weatherCountCursor(): Cursor
}
