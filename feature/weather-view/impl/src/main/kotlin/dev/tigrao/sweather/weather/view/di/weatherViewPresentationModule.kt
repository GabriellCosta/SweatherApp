package dev.tigrao.sweather.weather.view.di

import dev.tigrao.sweather.weather.view.domain.TemperatureSymbolConverter
import dev.tigrao.sweather.weather.view.presentation.ImageIconUrlFactory
import dev.tigrao.sweather.weather.view.presentation.MapFromWeatherModelToVO
import dev.tigrao.sweather.weather.view.presentation.WeatherViewViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

internal val weatherViewPresentationModule = module {
    factory {
        MapFromWeatherModelToVO(
            get(),
            get(),
        )
    }

    factory {
        TemperatureSymbolConverter()
    }

    factory {
        ImageIconUrlFactory()
    }

    viewModel {
        WeatherViewViewModel(
            get(),
            get(),
        )
    }
}