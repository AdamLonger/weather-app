package com.firethings.something.presentation

import com.firethings.something.common.core.BaseViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest

@ExperimentalCoroutinesApi
fun <S : Any, E : Any, VM : BaseViewModel<E, *, *, S>> runSimpleTest(
    viewModel: VM,
    testScope: TestScope,
    runEvents: (TestScope) -> Unit,
    assert: (List<S>) -> Unit
) = runTest {
    val newStates: MutableList<S> = mutableListOf()
    val collectJob = launch {
        viewModel.stateFlow.toList(newStates)
    }

    viewModel.onStart()

    runEvents(testScope)

    collectJob.cancel()

    assert(newStates)
}

@ExperimentalCoroutinesApi
fun <S : Any, E : Any, VM : BaseViewModel<E, *, *, S>> runEventTest(
    viewModel: VM,
    testScope: TestScope,
    events: List<E>,
    assert: (List<S>) -> Unit
) = runSimpleTest(
    viewModel,
    testScope,
    runEvents = { eventTestScope ->
        testScope.advanceUntilIdle()
        events.forEach { viewModel.sendEvent(it) }
        eventTestScope.advanceUntilIdle()
    },
    assert
)
