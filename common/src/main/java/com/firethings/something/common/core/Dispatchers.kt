package com.firethings.something.common.core

import kotlinx.coroutines.CoroutineDispatcher

@Suppress("VariableNaming")
interface Dispatchers {
    val Main: CoroutineDispatcher
    val IO: CoroutineDispatcher
    val Default: CoroutineDispatcher
    val Unconfined: CoroutineDispatcher
}
