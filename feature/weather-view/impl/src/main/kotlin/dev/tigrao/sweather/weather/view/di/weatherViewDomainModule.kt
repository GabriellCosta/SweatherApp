package dev.tigrao.sweather.weather.view.di

import com.google.android.gms.location.LocationServices
import dev.tigrao.sweather.weather.view.domain.*
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

internal val weatherViewDomainModule = module {
    single<FetchWeatherDataByGeoLocationUseCase> {
        FetchWeatherDataByGeoLocation(
            api = get(),
            get()
        )
    }

    factory<GetLocationUseCase> {
        GetLocation(
            LocationServices.getFusedLocationProviderClient(androidContext())
        )
    }

    single<GetDayInformationUseCase> {
        GetDayInformation(
            get()
        )
    }
}
