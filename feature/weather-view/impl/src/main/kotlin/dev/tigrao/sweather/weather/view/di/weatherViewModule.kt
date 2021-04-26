package dev.tigrao.sweather.weather.view.di

fun getWeatherViewModules() = listOf(
    weatherViewDataModule,
    weatherServiceModule,
    weatherViewDomainModule,
    weatherViewPresentationModule,
    weatherViewViewModule,
)
