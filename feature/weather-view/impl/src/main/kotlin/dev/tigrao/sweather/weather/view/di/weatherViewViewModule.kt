package dev.tigrao.sweather.weather.view.di

import dev.tigrao.sweather.weather.view.view.WeatherViewNavigation
import dev.tigrao.sweather.weather.view.view.WeatherViewNavigationDefault
import org.koin.dsl.module

internal val weatherViewViewModule = module {
    factory<WeatherViewNavigation> {
        WeatherViewNavigationDefault(get())
    }
}
