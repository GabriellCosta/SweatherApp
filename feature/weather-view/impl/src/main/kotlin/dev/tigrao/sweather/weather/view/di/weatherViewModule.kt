package dev.tigrao.sweather.weather.view.di

fun getWeatherViewModules() = listOf(
    weatherViewDataModule,
    weatherViewDomainModule,
    weatherViewPresentationModule,
    weatherViewViewModule,
)
