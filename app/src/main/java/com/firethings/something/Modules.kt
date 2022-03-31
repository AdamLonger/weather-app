package com.firethings.something

import com.firethings.something.data.api.WeatherClient
import com.firethings.something.data.local.LocalWeatherStorage
import com.firethings.something.presentation.DetailsViewModel
import com.firethings.something.presentation.MainViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val myModule = module {
    viewModel { MainViewModel(get(), get()) }
    viewModel { DetailsViewModel(get()) }
    single { WeatherClient() }
    single { LocalWeatherStorage.build(androidContext()) }
}
