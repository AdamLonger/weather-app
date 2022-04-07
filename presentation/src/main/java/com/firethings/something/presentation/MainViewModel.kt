package com.firethings.something.presentation

import com.firethings.something.common.core.BaseViewModel
import com.firethings.something.common.core.Loading
import com.firethings.something.common.core.StatePart
import com.firethings.something.common.core.mutateError
import com.firethings.something.common.core.mutateLoaded
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
        object SaveWeather : Event()
        data class LocationUpdated(val location: Coordinates) : Event()
    }

    sealed class Action {
        object SaveWeather : Action()
        object LoadLocalData : Action()
        data class LocationUpdated(val location: Coordinates) : Action()
    }

    sealed class Effect {
        object Loading : Effect()
        data class LocationUpdated(val location: Coordinates) : Effect()
        object WeatherSaved : Effect()
        data class WeatherSaveFailed(val throwable: Throwable) : Effect()
        data class LocalDataLoaded(val weatherList: List<Weather.Stored>) : Effect()
    }

    data class State(
        val isLoading: Boolean = true,
        val syncingData: StatePart<Weather.Simple, Throwable> = Loading,
        val localData: StatePart<List<Weather.Stored>, Throwable> = Loading,
        val location: Coordinates = Coordinates()
    )

    override val initialState: State = State()

    override val onStartActions: List<Action> = listOf(Action.LoadLocalData)

    override fun Event.toAction(): Action = when (this) {
        Event.SaveWeather -> Action.SaveWeather
        is Event.LocationUpdated -> Action.LocationUpdated(location)
    }

    override fun Action.perform(): Flow<Effect> = when (this) {
        Action.SaveWeather -> flow {
            emit(Effect.Loading)

            val data = weatherUseCase.getWeather(state.location)
            data?.let { weatherEntity ->
                weatherStorageUseCase.insertWeatherAndConditions(weatherEntity)
            }

            emit(Effect.WeatherSaved)
        }.catch { throwable ->
            emit(Effect.WeatherSaveFailed(throwable))
        }
        is Action.LocationUpdated -> flowOf(Effect.LocationUpdated(location))
        Action.LoadLocalData -> weatherStorageUseCase.weatherFlow().map { entity ->
            Effect.LocalDataLoaded(entity)
        }
    }

    override fun Effect.toNewState(): State = when (this) {
        Effect.Loading -> state.copy(isLoading = true)
        is Effect.WeatherSaveFailed -> state.copy(
            isLoading = false, localData = state.localData.mutateError(throwable))
        is Effect.WeatherSaved -> state.copy(isLoading = false)
        is Effect.LocationUpdated -> state.copy(location = location)
        is Effect.LocalDataLoaded -> state.copy(
            isLoading = false, localData = state.localData.mutateLoaded(weatherList))
    }
}
