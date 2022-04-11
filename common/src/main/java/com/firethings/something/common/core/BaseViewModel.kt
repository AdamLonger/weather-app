package com.firethings.something.common.core

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

abstract class BaseViewModel<Event : Any, Action : Any, Result : Any, State : Any>(
    private val dispatchers: Dispatchers
) : ViewModel() {

    abstract val initialState: State
    private val internalStateFlow: MutableStateFlow<State> by lazy { MutableStateFlow(initialState) }
    private var stateJob: Job? = null

    val stateFlow: StateFlow<State>
        get() = internalStateFlow

    val state: State
        get() = internalStateFlow.value

    protected val internalScope: CoroutineScope = viewModelScope

    open val onStartActions: List<Action> = emptyList()

    protected val actions: Channel<Action> = Channel()

    abstract fun Event.toAction(): Action

    abstract fun Action.perform(): Flow<Result>

    abstract fun Result.toNewState(): State

    fun onStart() {
        if (stateJob == null) {
            stateJob = startEventsProcessing()
        }
    }

    fun sendEvent(event: Event) {
        internalScope.launch {
            actions.send(event.toAction())
        }
    }

    private fun startEventsProcessing(): Job {
        return internalScope.launch {
            actions.consumeAsFlow().onStart {
                onStartActions.forEach { emit(it) }
            }.collect { action ->
                launch(dispatchers.Default + ViewModelExceptionHandler) {
                    action.perform().collect { result ->
                        internalStateFlow.value = result.toNewState()
                    }
                }
            }
        }
    }
}
