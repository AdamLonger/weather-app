package com.firethings.something

import com.firethings.something.common.LocationClient
import com.firethings.something.common.core.DefaultDispatchers
import com.firethings.something.common.core.Dispatchers
import com.firethings.something.data.api.WeatherClient
import com.firethings.something.data.local.LocalWeatherStorage
import com.firethings.something.data.usecase.DefaultWeatherStorageUseCase
import com.firethings.something.data.usecase.DefaultWeatherUseCase
import com.firethings.something.domain.usecase.WeatherStorageUseCase
import com.firethings.something.domain.usecase.WeatherUseCase
import com.firethings.something.presentation.DetailsViewModel
import com.firethings.something.presentation.EditorViewModel
import com.firethings.something.presentation.MainViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val myModule = module {
    single { WeatherClient() }
    single { LocalWeatherStorage.build(androidContext()) }
    single<LocationClient> { DefaultLocationClient() }

    single<Dispatchers> { DefaultDispatchers() }
    single<WeatherStorageUseCase> { DefaultWeatherStorageUseCase(get()) }
    single<WeatherUseCase> { DefaultWeatherUseCase(get()) }

    viewModel { MainViewModel(get(), get(), get()) }
    viewModel { DetailsViewModel(get(), get()) }
    viewModel { EditorViewModel(get(), get()) }
}
