package com.firethings.something.presentation

import com.firethings.something.common.core.Dispatchers
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher

data class TestDispatchers @ExperimentalCoroutinesApi constructor(
    val testDispatcher: CoroutineDispatcher = StandardTestDispatcher(),
    override val Main: CoroutineDispatcher = testDispatcher,
    override val IO: CoroutineDispatcher = testDispatcher,
    override val Default: CoroutineDispatcher = testDispatcher,
    override val Unconfined: CoroutineDispatcher = testDispatcher,
) : Dispatchers
