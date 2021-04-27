package dev.tigrao.sweather.weather.view.domain

import dev.tigrao.sweather.weather.view.R

internal interface GetBackgroundUseCase {
    operator fun invoke(isDay: Boolean): Int
}

internal class GetBackground : GetBackgroundUseCase {
    override fun invoke(isDay: Boolean): Int =
        if (isDay) R.drawable.bg_weather_view_day else R.drawable.bg_weather_view_night
}
