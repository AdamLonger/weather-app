package com.firethings.something.presentation

import com.firethings.something.common.core.BaseViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest

@ExperimentalCoroutinesApi
fun <S : Any, E : Any, VM : BaseViewModel<E, *, *, S>> runEventTest(
    viewModel: VM,
    testScope: TestScope,
    events: List<E>,
    assert: (List<S>) -> Unit
) = runTest {
    val newStates: MutableList<S> = mutableListOf()
    val collectJob = launch {
        viewModel.stateFlow.toList(newStates)
    }

    viewModel.onStart()
    events.forEach { viewModel.sendEvent(it) }
    testScope.advanceUntilIdle()

    collectJob.cancel()

    assert(newStates)
}
