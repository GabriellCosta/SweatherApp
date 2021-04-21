package dev.tigrao.sweather.weather.view.di

import dev.tigrao.sweather.weather.view.data.WeatherViewApi
import org.koin.dsl.module
import retrofit2.Retrofit

internal val weatherViewDataModule = module {
    single {
        val retrofit = get<Retrofit>()

        retrofit.create(WeatherViewApi::class.java)
    }
}
