package com.firethings.something.common.core

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

abstract class BaseViewModel<Event : Any, Action : Any, Result : Any, State : Any> : ViewModel() {

    abstract val initialState: State
    private val internalStateFlow: MutableStateFlow<State> by lazy { MutableStateFlow(initialState) }

    val stateFlow: StateFlow<State>
        get() = internalStateFlow

    val state: State
        get() = internalStateFlow.value

    open val onStartActions: List<Action> = emptyList()

    private val actions: Channel<Action> = Channel()

    abstract fun Event.toAction(): Action

    abstract fun Action.perform(): Flow<Result>

    abstract fun Result.toNewState(): State

    init {
        startEventsProcessing()
    }

    fun sendEvent(event: Event) {
        viewModelScope.launch {
            actions.send(event.toAction())
        }
    }

    private fun startEventsProcessing() {
        viewModelScope.launch {
            actions.consumeAsFlow().onStart {
                onStartActions.forEach { emit(it) }
            }.collect { action ->
                launch(Dispatchers.Default + ViewModelExceptionHandler) {
                    action.perform().collect { result -> internalStateFlow.value = result.toNewState() }
                }
            }
        }
    }
}
