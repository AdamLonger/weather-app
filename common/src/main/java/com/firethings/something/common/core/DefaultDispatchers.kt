package com.firethings.something.common.core

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers as CoroutineDispatchers

data class DefaultDispatchers(
    override val Main: CoroutineDispatcher = CoroutineDispatchers.Main,
    override val IO: CoroutineDispatcher = CoroutineDispatchers.IO,
    override val Default: CoroutineDispatcher = CoroutineDispatchers.Default,
    override val Unconfined: CoroutineDispatcher = CoroutineDispatchers.Unconfined
) : Dispatchers
