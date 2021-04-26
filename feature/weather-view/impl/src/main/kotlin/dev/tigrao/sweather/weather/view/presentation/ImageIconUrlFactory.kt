package dev.tigrao.sweather.weather.view.presentation

import dev.tigrao.sweather.weather.view.BuildConfig

private const val REPLACE_TARGET = "{ID}"

internal class ImageIconUrlFactory {

    fun create(icon: String): String {
        return BuildConfig.IMAGE_URL.replace(REPLACE_TARGET, icon)
    }
}
