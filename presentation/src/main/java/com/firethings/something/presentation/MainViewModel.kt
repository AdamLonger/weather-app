package com.firethings.something.presentation

import com.firethings.something.common.core.BaseViewModel
import com.firethings.something.domain.model.Coordinates
import com.firethings.something.domain.model.Weather
import com.firethings.something.domain.usecase.WeatherStorageUseCase
import com.firethings.something.domain.usecase.WeatherUseCase
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
    private val weatherUseCase: WeatherUseCase,
    private val weatherStorageUseCase: WeatherStorageUseCase
) : BaseViewModel<Event, Action, Effect, State>() {
    sealed class Event {
        object LoadWeather : Event()
        data class LocationUpdated(val location: Coordinates) : Event()
    }

    sealed class Action {
        object LoadWeather : Action()
        object LoadLocalData : Action()
        data class LocationUpdated(val location: Coordinates) : Action()
    }

    sealed class Effect {
        object Loading : Effect()
        data class LocationUpdated(val location: Coordinates) : Effect()
        object WeatherLoaded : Effect()
        data class WeatherLoadFailed(val throwable: Throwable) : Effect()
        data class LocalDataLoaded(val weatherList: List<Weather.Stored>) : Effect()
    }

    data class State(
        val isLoading: Boolean = false,
        val latestData: Weather.Simple? = null,
        val localData: List<Weather.Stored> = emptyList(),
        val error: Throwable? = null,
        val location: Coordinates = Coordinates()
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

            val data = weatherUseCase.getWeather(state.location)
            data?.let { weatherEntity ->
                weatherStorageUseCase.insertWeatherAndConditions(weatherEntity)
            }

            emit(Effect.WeatherLoaded)
        }.catch { throwable ->
            emit(Effect.WeatherLoadFailed(throwable))
        }
        is Action.LocationUpdated -> flowOf(Effect.LocationUpdated(location))
        Action.LoadLocalData -> weatherStorageUseCase.weatherFlow().map { entity ->
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
