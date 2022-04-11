package com.firethings.something.presentation

import com.firethings.something.common.core.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.dsl.module

@ExperimentalCoroutinesApi
val testModules = module {
    single<Dispatchers> { TestDispatchers() }
}
