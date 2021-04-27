package dev.tigrao.sweather.weather.view.presentation.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

internal data class WeatherItemVO(
    @DrawableRes val icon: Int,
    val value: String,
    @StringRes val name: Int,
)
