package com.firethings.something.presentation

import com.firethings.something.core.BaseViewModel
import com.firethings.something.data.local.LocalWeatherStorage
import com.firethings.something.data.local.model.WeatherWithConditions
import com.firethings.something.presentation.DetailsViewModel.Action
import com.firethings.something.presentation.DetailsViewModel.Effect
import com.firethings.something.presentation.DetailsViewModel.Event
import com.firethings.something.presentation.DetailsViewModel.State
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DetailsViewModel(
    private val localWeatherStorage: LocalWeatherStorage
) : BaseViewModel<Event, Action, Effect, State>() {
    sealed class Event {
        data class LoadWeatherData(val id: Int) : Event()
    }

    sealed class Action {
        data class LoadWeatherData(val id: Int) : Action()
    }

    sealed class Effect {
        object Loading : Effect()
        data class DataLoadFailed(val throwable: Throwable) : Effect()
        data class DataLoaded(val weather: WeatherWithConditions) : Effect()
    }

    data class State(
        val isLoading: Boolean = false,
        val weather: WeatherWithConditions? = null,
        val error: Throwable? = null,
    )

    override val initialState: State = State()

    override fun Event.toAction(): Action = when (this) {
        is Event.LoadWeatherData -> Action.LoadWeatherData(id)
    }

    override fun Action.perform(): Flow<Effect> = when (this) {
        is Action.LoadWeatherData -> flow {
            emit(Effect.Loading)

            val entity = localWeatherStorage.weatherDao().weatherById(id)
            val effect = entity?.let { Effect.DataLoaded(entity) } ?: Effect.DataLoadFailed(Throwable("No such entry"))
            emit(effect)
        }
    }

    override fun Effect.toNewState(): State = when (this) {
        Effect.Loading -> state.copy(isLoading = true)
        is Effect.DataLoadFailed -> state.copy(isLoading = false, error = throwable, weather = null)
        is Effect.DataLoaded -> state.copy(isLoading = false, error = null, weather = weather)
    }
}
