package com.firethings.something.data.usecase

import com.firethings.something.data.local.LocalWeatherStorage
import com.firethings.something.data.mappers.getBaseEntry
import com.firethings.something.data.mappers.getConditionEntryList
import com.firethings.something.data.mappers.toDomain
import com.firethings.something.domain.model.Weather
import com.firethings.something.domain.usecase.WeatherStorageUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class DefaultWeatherStorageUseCase(
    private val storage: LocalWeatherStorage
) : WeatherStorageUseCase {
    override fun weatherFlow(): Flow<List<Weather.Stored>> = storage.weatherDao().weatherFlow().map { list ->
        list.map { data -> data.toDomain() }
    }

    override suspend fun weatherById(id: Long): Weather.Stored? = withContext(Dispatchers.IO) {
        storage.weatherDao().weatherById(id)?.toDomain()
    }

    override suspend fun insertWeatherAndConditions(weather: Weather) =
        withContext(Dispatchers.IO) {
            storage.weatherDao().insertEntryWithConditions(weather.getBaseEntry(), weather.getConditionEntryList())
        }

    override suspend fun updateWeather(weather: Weather): Unit =
        withContext(Dispatchers.IO) {
            storage.weatherDao().updateEntry(weather.getBaseEntry())
        }

    override suspend fun deleteById(id: Long) = withContext(Dispatchers.IO) {
        storage.weatherDao().deleteWeatherAndConditionsById(id)
    }
}
