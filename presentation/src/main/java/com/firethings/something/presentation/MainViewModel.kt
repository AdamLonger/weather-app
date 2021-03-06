package com.firethings.something.presentation

import com.firethings.something.common.core.BaseViewModel
import com.firethings.something.common.core.Dispatchers
import com.firethings.something.common.core.Failed
import com.firethings.something.common.core.Loaded
import com.firethings.something.common.core.Loading
import com.firethings.something.common.core.StatePart
import com.firethings.something.common.throttleFirst
import com.firethings.something.domain.model.Coordinates
import com.firethings.something.domain.model.Weather
import com.firethings.something.domain.usecase.WeatherStorageUseCase
import com.firethings.something.domain.usecase.WeatherUseCase
import com.firethings.something.presentation.MainViewModel.Action
import com.firethings.something.presentation.MainViewModel.Effect
import com.firethings.something.presentation.MainViewModel.Event
import com.firethings.something.presentation.MainViewModel.State
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class MainViewModel(
    private val weatherUseCase: WeatherUseCase,
    private val weatherStorageUseCase: WeatherStorageUseCase,
    dispatchers: Dispatchers
) : BaseViewModel<Event, Action, Effect, State>(dispatchers) {
    sealed class Event {
        object SaveWeather : Event()
        data class LocationUpdated(val location: Coordinates) : Event()
        object StartPeriodicUpdates : Event()
        object StopPeriodicUpdates : Event()
    }

    sealed class Action {
        object SaveWeather : Action()
        object LoadLocalData : Action()
        data class LocationUpdated(val location: Coordinates) : Action()
        object StartPeriodicUpdates : Action()
        object StopPeriodicUpdates : Action()
        data class UpdatePeriodicData(val location: Coordinates) : Action()
    }

    sealed class Effect {
        object Loading : Effect()
        data class LocationUpdated(val location: Coordinates) : Effect()
        object WeatherSaved : Effect()
        data class WeatherSaveFailed(val throwable: Throwable) : Effect()
        data class LocalDataLoaded(val weatherList: List<Weather.Stored>) : Effect()

        data class PeriodicUpdateChanged(val enabled: Boolean) : Effect()
        data class PeriodicUpdateSuccess(val weather: Weather.Simple?) : Effect()
        data class PeriodicUpdateFailed(val throwable: Throwable) : Effect()
    }

    data class State(
        val isLoading: Boolean = true,
        val periodicEnabled: Boolean = false,
        val refreshingData: StatePart<Weather.Simple?, Throwable> = Loading,
        val localData: StatePart<List<Weather.Stored>, Throwable> = Loading,
        val location: Coordinates = Coordinates()
    )

    override val initialState: State = State()

    override val onStartActions: List<Action> = listOf(Action.LoadLocalData, Action.StartPeriodicUpdates)

    private var refreshingJob: Job? = null

    private var periodicFlow: MutableStateFlow<Coordinates> = MutableStateFlow(Coordinates())

    override fun Event.toAction(): Action = when (this) {
        Event.SaveWeather -> Action.SaveWeather
        is Event.LocationUpdated -> Action.LocationUpdated(location)
        Event.StartPeriodicUpdates -> Action.StartPeriodicUpdates
        Event.StopPeriodicUpdates -> Action.StopPeriodicUpdates
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
        is Action.LocationUpdated -> flow {
            periodicFlow.value = location
            emit(Effect.LocationUpdated(location))
        }
        Action.LoadLocalData -> weatherStorageUseCase.weatherFlow().map { entity ->
            Effect.LocalDataLoaded(entity)
        }
        Action.StartPeriodicUpdates -> flow {
            if (refreshingJob != null) return@flow
            refreshingJob = internalScope.launch {
                periodicFlow.throttleFirst(REFRESH_PERIOD).distinctUntilChanged().collect {
                    actions.send(Action.UpdatePeriodicData(it))
                }
            }
            emit(Effect.PeriodicUpdateChanged(true))
        }
        Action.StopPeriodicUpdates -> flow {
            refreshingJob?.cancel()
            refreshingJob = null
            emit(Effect.PeriodicUpdateChanged(false))
        }
        is Action.UpdatePeriodicData -> flow<Effect> {
            val data = weatherUseCase.getWeather(location)
            emit(Effect.PeriodicUpdateSuccess(data))
        }.catch { throwable ->
            emit(Effect.PeriodicUpdateFailed(throwable))
        }
    }

    override fun Effect.toNewState(): State = when (this) {
        Effect.Loading -> state.copy(isLoading = true)
        is Effect.WeatherSaveFailed -> state.copy(
            isLoading = false, localData = Failed(throwable)
        )
        is Effect.WeatherSaved -> state.copy(isLoading = false)
        is Effect.LocationUpdated -> state.copy(location = location)
        is Effect.LocalDataLoaded -> state.copy(
            isLoading = false, localData = Loaded(weatherList)
        )
        is Effect.PeriodicUpdateFailed -> state.copy(refreshingData = Failed(throwable))
        is Effect.PeriodicUpdateSuccess -> state.copy(refreshingData = Loaded(weather))
        is Effect.PeriodicUpdateChanged -> state.copy(
            periodicEnabled = enabled,
            refreshingData = if (enabled) state.refreshingData else Loaded(null)
        )
    }

    companion object {
        private const val REFRESH_PERIOD = 3000L
    }
}
