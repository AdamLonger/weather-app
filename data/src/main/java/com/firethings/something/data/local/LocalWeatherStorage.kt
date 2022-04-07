package com.firethings.something.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.firethings.something.data.local.model.WeatherConditionEntry
import com.firethings.something.data.local.model.WeatherEntry

@Database(
    entities = [
        WeatherConditionEntry::class,
        WeatherEntry::class
    ],
    version = LocalWeatherStorage.WEATHER_STORAGE_VERSION
)
@TypeConverters(Converters::class)
abstract class LocalWeatherStorage : RoomDatabase() {
    abstract fun weatherDao(): WeatherDao
    abstract fun simpleWeatherDao(): SimpleWeatherDao

    companion object {
        const val WEATHER_STORAGE_VERSION = 1
        private const val WEATHER_DATABASE = "weather_database"

        fun build(applicationContext: Context) = Room.databaseBuilder(
            applicationContext,
            LocalWeatherStorage::class.java, WEATHER_DATABASE
        ).build()
    }
}
