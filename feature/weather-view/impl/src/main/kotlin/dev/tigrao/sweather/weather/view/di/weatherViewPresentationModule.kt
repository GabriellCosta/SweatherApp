package dev.tigrao.sweather.weather.view.di

import dev.tigrao.sweather.weather.view.BuildConfig
import dev.tigrao.sweather.weather.view.domain.TemperatureSymbolConverter
import dev.tigrao.sweather.weather.view.presentation.ImageIconUrlFactory
import dev.tigrao.sweather.weather.view.presentation.MapFromWeatherModelToVO
import dev.tigrao.sweather.weather.view.presentation.WeatherListConditionsFactory
import dev.tigrao.sweather.weather.view.presentation.WeatherViewViewModel
import dev.tigrao.sweather.weather.view.presentation.model.WeatherViewState
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

internal val weatherViewPresentationModule = module {
    factory {
        MapFromWeatherModelToVO(
            get(),
            get(),
            get(),
            get(),
            get(),
        )
    }

    factory {
        TemperatureSymbolConverter()
    }

    factory {
        ImageIconUrlFactory(
            BuildConfig.IMAGE_URL,
        )
    }

    viewModel {
        WeatherViewViewModel(
            get(),
            get(),
            get()
        )
    }

    factory { WeatherViewState() }

    factory {
        WeatherListConditionsFactory()
    }
}
