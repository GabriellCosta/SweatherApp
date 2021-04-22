package dev.tigrao.sweather.weather.view.di

import dev.tigrao.sweather.weather.view.domain.FetchWeatherDataByGeoLocation
import dev.tigrao.sweather.weather.view.domain.FetchWeatherDataByGeoLocationUseCase
import org.koin.dsl.module

internal val weatherViewDomainModule = module {
    single<FetchWeatherDataByGeoLocationUseCase> {
        FetchWeatherDataByGeoLocation(
            api = get()
        )
    }
}
