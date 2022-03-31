package com.firethings.something.presentation

import com.firethings.something.core.BaseViewModel
import com.firethings.something.data.api.WeatherClient
import com.firethings.something.data.local.LocalWeatherStorage
import com.firethings.something.data.local.model.WeatherWithConditions
import com.firethings.something.data.local.toConditionEntities
import com.firethings.something.data.local.toEntity
import com.firethings.something.presentation.MainViewModel.Action
import com.firethings.something.presentation.MainViewModel.Effect
import com.firethings.something.presentation.MainViewModel.Event
import com.firethings.something.presentation.MainViewModel.State
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

class MainViewModel(
    private val weatherClient: WeatherClient,
    private val localWeatherStorage: LocalWeatherStorage
) : BaseViewModel<Event, Action, Effect, State>() {
    sealed class Event {
        object LoadWeather : Event()
        data class LocationUpdated(val location: LatLng) : Event()
    }

    sealed class Action {
        object LoadWeather : Action()
        object LoadLocalData : Action()
        data class LocationUpdated(val location: LatLng) : Action()
    }

    sealed class Effect {
        object Loading : Effect()
        data class LocationUpdated(val location: LatLng) : Effect()
        object WeatherLoaded : Effect()
        data class WeatherLoadFailed(val throwable: Throwable) : Effect()
        data class LocalDataLoaded(val weatherList: List<WeatherWithConditions>) : Effect()
    }

    data class State(
        val isLoading: Boolean = false,
        val latestData: WeatherWithConditions? = null,
        val localData: List<WeatherWithConditions> = emptyList(),
        val error: Throwable? = null,
        val location: LatLng = LatLng()
    )

    override val initialState: State = State()

    override val onStartActions: List<Action> = listOf(Action.LoadLocalData)

    override fun Event.toAction(): Action = when (this) {
        Event.LoadWeather -> Action.LoadWeather
        is Event.LocationUpdated -> Action.LocationUpdated(location)
    }

    override fun Action.perform(): Flow<Effect> = when (this) {
        Action.LoadWeather -> flow {
            emit(Effect.Loading)

            val data = weatherClient.getWeather(state.location)
            data?.toEntity()?.let { weatherEntity ->
                localWeatherStorage.weatherDao().insertEntry(weatherEntity)
                data.toConditionEntities().forEach { conditionEntity ->
                    localWeatherStorage.weatherDao()
                        .insertConditionEntry(conditionEntity.copy(parentId = weatherEntity.id))
                }
            }

            emit(Effect.WeatherLoaded)
        }.catch { throwable ->
            emit(Effect.WeatherLoadFailed(throwable))
        }
        is Action.LocationUpdated -> flowOf(Effect.LocationUpdated(location))
        Action.LoadLocalData -> localWeatherStorage.weatherDao().weatherFlow().map { entity ->
            Effect.LocalDataLoaded(entity)
        }
    }

    override fun Effect.toNewState(): State = when (this) {
        Effect.Loading -> state.copy(isLoading = true)
        is Effect.WeatherLoadFailed -> state.copy(isLoading = false, latestData = null, error = throwable)
        is Effect.WeatherLoaded -> state.copy(isLoading = false, error = null)
        is Effect.LocationUpdated -> state.copy(location = location)
        is Effect.LocalDataLoaded -> state.copy(localData = weatherList)
    }
}
