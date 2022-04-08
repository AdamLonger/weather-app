package com.firethings.something.presentation

import com.firethings.something.domain.usecase.WeatherStorageUseCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class DetailsViewModelTest {
    @MockK
    lateinit var storageUseCase: WeatherStorageUseCase
    private val viewModel: DetailsViewModel by lazy { DetailsViewModel(storageUseCase) }


    @Before
    fun setUp() = MockKAnnotations.init(this)

    @ExperimentalCoroutinesApi
    @Test
    fun test() = runTest {
        coEvery { storageUseCase.deleteById(any()) } returns Unit

        viewModel.sendEvent(DetailsViewModel.Event.DeleteEntry(0))
    }
}
