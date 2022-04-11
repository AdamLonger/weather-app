package com.firethings.something.presentation

import com.firethings.something.domain.usecase.WeatherStorageUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import org.koin.test.inject
import com.firethings.something.common.core.Dispatchers as CoreDispatchers

@ExperimentalCoroutinesApi
class DetailsViewModelTest : KoinTest {
    private val storageUseCase: WeatherStorageUseCase = TestWeatherStorageUseCase()
    private val dispatchers by inject<CoreDispatchers>()
    private lateinit var viewModel: DetailsViewModel

    private lateinit var testScope: TestScope

    @get:Rule
    val koinTestRule = KoinTestRule.create {
        modules(testModules)
    }

    @Before
    fun setup() {
        val dispatcher = StandardTestDispatcher()
        testScope = TestScope(dispatcher)
        Dispatchers.setMain(dispatcher)
        viewModel = DetailsViewModel(storageUseCase, dispatchers)
    }

    @After
    fun tearDown() {
        testScope.cancel()
        Dispatchers.resetMain()
    }

    @Test
    fun `Load weather details`() = runEventTest(
        viewModel,
        testScope,
        listOf(DetailsViewModel.Event.LoadWeatherData(0))
    ) { newStates ->
        val latest: DetailsViewModel.State = newStates.last()
        assert(latest.weather.isSuccess)
        assert(latest.weather.getOrNull() == TestWeatherStorageUseCase.testWeather)
    }

    @Test
    fun `Delete weather details`() = runEventTest(
        viewModel,
        testScope,
        listOf(DetailsViewModel.Event.DeleteEntry(0))
    ) { newStates ->
        val latest: DetailsViewModel.State = newStates.last()
        assert(latest.deleted)
    }
}
