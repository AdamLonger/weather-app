package com.firethings.something.presentation

import com.firethings.something.common.core.BaseViewModel
import com.firethings.something.common.core.Dispatchers
import com.firethings.something.common.core.Failed
import com.firethings.something.common.core.Loaded
import com.firethings.something.common.core.Loading
import com.firethings.something.common.core.StatePart
import com.firethings.something.domain.model.Coordinates
import com.firethings.something.domain.model.MainDetails
import com.firethings.something.domain.model.Temperature
import com.firethings.something.domain.model.Weather
import com.firethings.something.domain.usecase.WeatherStorageUseCase
import com.firethings.something.presentation.EditorViewModel.Action
import com.firethings.something.presentation.EditorViewModel.Effect
import com.firethings.something.presentation.EditorViewModel.Event
import com.firethings.something.presentation.EditorViewModel.State
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf

class EditorViewModel(
    private val weatherStorageUseCase: WeatherStorageUseCase,
    dispatchers: Dispatchers
) : BaseViewModel<Event, Action, Effect, State>(dispatchers) {
    sealed class Event {
        object SaveUpdates : Event()
        data class LoadWeatherData(val id: Long) : Event()
        data class SetLat(val lat: Float?) : Event()
        data class SetLon(val lon: Float?) : Event()
        data class SetTemp(val temp: Float?) : Event()
    }

    sealed class Action {
        object SaveUpdates : Action()
        data class LoadWeatherData(val id: Long) : Action()
        data class SetLat(val lat: Float?) : Action()
        data class SetLon(val lon: Float?) : Action()
        data class SetTemp(val temp: Float?) : Action()
    }

    sealed class Effect {
        object Loading : Effect()
        object SaveSuccess : Effect()
        object SaveFailed : Effect()
        data class DataLoadFailed(val throwable: Throwable) : Effect()
        data class DataLoaded(val weather: Weather) : Effect()
        data class LatSet(val lat: Float?) : Effect()
        data class LonSet(val lon: Float?) : Effect()
        data class TempSet(val temp: Float?) : Effect()
    }

    data class State(
        val isLoading: Boolean = false,
        val saved: Boolean = false,
        val newLat: Float? = null,
        val newLon: Float? = null,
        val newTemp: Float? = null,
        val weather: StatePart<Weather, Throwable> = Loading
    )

    override val initialState: State = State()

    override fun Event.toAction(): Action = when (this) {
        is Event.LoadWeatherData -> Action.LoadWeatherData(id)
        is Event.SaveUpdates -> Action.SaveUpdates
        is Event.SetLat -> Action.SetLat(lat)
        is Event.SetLon -> Action.SetLon(lon)
        is Event.SetTemp -> Action.SetTemp(temp)
    }

    @Suppress("ComplexMethod")
    override fun Action.perform(): Flow<Effect> = when (this) {
        is Action.LoadWeatherData -> flow {
            emit(Effect.Loading)

            val data = weatherStorageUseCase.weatherById(id)
            val effect = data?.let { Effect.DataLoaded(it) } ?: Effect.DataLoadFailed(Throwable("No such entry"))
            emit(effect)
        }
        is Action.SaveUpdates -> flow {
            emit(Effect.Loading)

            val weather = state.weather.getOrNull() as? Weather.Stored
            if (weather != null) {
                val updated = weather.copy(
                    coordinates = Coordinates(
                        lat = state.newLat ?: weather.coordinates.lat,
                        lon = state.newLon ?: weather.coordinates.lon
                    ),
                    main = (weather.main ?: MainDetails()).copy(
                        temp = state.newTemp?.let { Temperature.Celsius(it) }
                            ?: weather.main?.temp
                            ?: Temperature.Celsius(0f)
                    )
                )
                weatherStorageUseCase.updateWeather(updated)
                emit(Effect.SaveSuccess)
            } else {
                emit(Effect.SaveFailed)
            }
        }
        is Action.SetLat -> flowOf(Effect.LatSet(lat))
        is Action.SetLon -> flowOf(Effect.LonSet(lon))
        is Action.SetTemp -> flowOf(Effect.TempSet(temp))
    }

    override fun Effect.toNewState(): State = when (this) {
        Effect.Loading -> state.copy(isLoading = true)
        is Effect.DataLoadFailed -> state.copy(isLoading = false, weather = Failed(throwable))
        is Effect.DataLoaded -> state.copy(isLoading = false, weather = Loaded(weather))
        Effect.SaveSuccess -> state.copy(isLoading = false, saved = true)
        Effect.SaveFailed -> state.copy(isLoading = false)
        is Effect.LatSet -> state.copy(newLat = lat)
        is Effect.LonSet -> state.copy(newLon = lon)
        is Effect.TempSet -> state.copy(newTemp = temp)
    }
}
