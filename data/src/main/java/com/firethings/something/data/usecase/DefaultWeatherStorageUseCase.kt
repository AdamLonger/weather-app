package com.firethings.something.data.usecase

import com.firethings.something.data.local.LocalWeatherStorage
import com.firethings.something.data.mappers.getBaseEntry
import com.firethings.something.data.mappers.getConditionEntryList
import com.firethings.something.data.mappers.toDomain
import com.firethings.something.domain.model.Weather
import com.firethings.something.domain.usecase.WeatherStorageUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DefaultWeatherStorageUseCase(
    private val storage: LocalWeatherStorage
) : WeatherStorageUseCase {
    override fun weatherFlow(): Flow<List<Weather.Stored>> = storage.weatherDao().weatherFlow().map { list ->
        list.map { data -> data.toDomain() }
    }

    override suspend fun weatherById(id: Int): Weather.Stored? = storage.weatherDao().weatherById(id)?.toDomain()

    override suspend fun insertWeatherAndConditions(weather: Weather) =
        storage.weatherDao().insertEntryWithConditions(weather.getBaseEntry(), weather.getConditionEntryList())

    override suspend fun deleteById(id: Int) = storage.weatherDao().deleteWeatherAndConditionsById(id)
}
