package com.firethings.something.presentation

import com.firethings.something.domain.usecase.WeatherStorageUseCase
import com.firethings.something.presentation.EditorViewModel.Event
import com.firethings.something.presentation.EditorViewModel.State
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
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
class EditorViewModelTest : KoinTest {
    private val storageUseCase: WeatherStorageUseCase = TestWeatherStorageUseCase()
    private val dispatchers by inject<CoreDispatchers>()

    private lateinit var viewModel: EditorViewModel

    private lateinit var testScope: TestScope

    @get:Rule
    val koinTestRule = KoinTestRule.create {
        modules(testModules)
    }

    @Before
    fun setup() {
        testScope = TestScope(dispatchers.Default)
        Dispatchers.setMain(dispatchers.Default)
        viewModel = EditorViewModel(storageUseCase, dispatchers)
    }

    @After
    fun tearDown() {
        testScope.cancel()
        Dispatchers.resetMain()
    }

    @Test
    fun `Load weather data`() = runEventTest(
        viewModel,
        testScope,
        listOf(Event.LoadWeatherData(0))
    ) { newStates ->
        val latest: State = newStates.last()
        assert(latest.weather.isSuccess)
        assert(latest.weather.getOrNull() == TestWeatherStorageUseCase.testWeather)
    }

    @Test
    fun `Set data for weather and save`() = runTest {
        val newStates: MutableList<State> = mutableListOf()
        val collectJob = launch {
            viewModel.stateFlow.toList(newStates)
        }

        viewModel.onStart()
        testScope.advanceUntilIdle()

        viewModel.sendEvent(Event.LoadWeatherData(0))
        testScope.advanceUntilIdle()

        viewModel.sendEvent(Event.SetTemp(TEST_TEMP))
        viewModel.sendEvent(Event.SetLat(TEST_LAT))
        viewModel.sendEvent(Event.SetLon(TEST_LON))
        viewModel.sendEvent(Event.SaveUpdates)

        testScope.advanceUntilIdle()

        collectJob.cancel()

        val latest: State = newStates.last()
        assert(latest.newLat == TEST_LAT)
        assert(latest.newLon == TEST_LON)
        assert(latest.newTemp == TEST_TEMP)
        assert(latest.saved)
    }

    companion object {
        private const val TEST_LAT = 39f
        private const val TEST_LON = 0.75f
        private const val TEST_TEMP = 4.9f
    }
}
