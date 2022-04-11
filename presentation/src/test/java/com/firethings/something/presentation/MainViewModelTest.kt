package com.firethings.something.presentation

import com.firethings.something.domain.model.Coordinates
import com.firethings.something.domain.usecase.WeatherStorageUseCase
import com.firethings.something.domain.usecase.WeatherUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
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
class MainViewModelTest : KoinTest {
    private val storageUseCase: WeatherStorageUseCase = TestWeatherStorageUseCase()
    private val weatherUseCase: WeatherUseCase = TestWeatherUseCase()
    private val dispatchers by inject<CoreDispatchers>()

    private lateinit var viewModel: MainViewModel

    private lateinit var testScope: TestScope

    @get:Rule
    val koinTestRule = KoinTestRule.create {
        modules(testModules)
    }

    @Before
    fun setup() {
        testScope = TestScope(dispatchers.Default)
        Dispatchers.setMain(dispatchers.Default)
        viewModel = MainViewModel(weatherUseCase, storageUseCase, dispatchers)
    }

    @After
    fun tearDown() {
        testScope.cancel()
        Dispatchers.resetMain()
    }

    @Test
    fun `Save weather`() = runEventTest(
        viewModel,
        testScope,
        listOf(MainViewModel.Event.SaveWeather)
    ) { newStates ->
        val latest: MainViewModel.State = newStates.last()
        assert(!latest.isLoading)
    }

    @Test
    fun `Start periodic updates`() = runEventTest(
        viewModel,
        testScope,
        listOf(MainViewModel.Event.StartPeriodicUpdates)
    ) { newStates ->
        val latest: MainViewModel.State = newStates.last()
        assert(!latest.isLoading)
        assert(latest.periodicEnabled)
    }

    @Test
    fun `Stop periodic updates`() = runEventTest(
        viewModel,
        testScope,
        listOf(MainViewModel.Event.StopPeriodicUpdates)
    ) { newStates ->
        val latest: MainViewModel.State = newStates.last()
        assert(!latest.isLoading)
        assert(!latest.periodicEnabled)
    }

    @Test
    @Suppress("MagicNumber")
    fun `Send Location Update`() = runEventTest(
        viewModel,
        testScope,
        listOf(MainViewModel.Event.LocationUpdated(Coordinates(50f, 60f)))
    ) { newStates ->
        val latest: MainViewModel.State = newStates.last()
        assert(!latest.isLoading)
        assert(latest.refreshingData.getOrNull()?.coordinates?.lat == 50f)
        assert(latest.refreshingData.getOrNull()?.coordinates?.lat == 60f)
    }
}
