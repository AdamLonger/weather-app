package com.firethings.something.presentation

import com.firethings.something.common.core.BaseViewModel
import com.firethings.something.domain.model.Weather
import com.firethings.something.domain.usecase.WeatherStorageUseCase
import com.firethings.something.presentation.DetailsViewModel.Action
import com.firethings.something.presentation.DetailsViewModel.Effect
import com.firethings.something.presentation.DetailsViewModel.Event
import com.firethings.something.presentation.DetailsViewModel.State
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DetailsViewModel(
    private val weatherStorageUseCase: WeatherStorageUseCase
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
        data class DataLoaded(val weather: Weather) : Effect()
    }

    data class State(
        val isLoading: Boolean = false,
        val weather: Weather? = null,
        val error: Throwable? = null,
    )

    override val initialState: State = State()

    override fun Event.toAction(): Action = when (this) {
        is Event.LoadWeatherData -> Action.LoadWeatherData(id)
    }

    override fun Action.perform(): Flow<Effect> = when (this) {
        is Action.LoadWeatherData -> flow {
            emit(Effect.Loading)

            val data = weatherStorageUseCase.weatherById(id)
            val effect = data?.let { Effect.DataLoaded(it) } ?: Effect.DataLoadFailed(Throwable("No such entry"))
            emit(effect)
        }
    }

    override fun Effect.toNewState(): State = when (this) {
        Effect.Loading -> state.copy(isLoading = true)
        is Effect.DataLoadFailed -> state.copy(isLoading = false, error = throwable, weather = null)
        is Effect.DataLoaded -> state.copy(isLoading = false, error = null, weather = weather)
    }
}
