package dev.tigrao.sweather.weather.view.di

import dev.tigrao.sweather.weather.view.service.TimeOfDayService
import org.koin.dsl.module

internal val weatherServiceModule = module {
    single {
        TimeOfDayService()
    }
}
