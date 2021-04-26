package dev.tigrao.sweather.weather.view.presentation

private const val REPLACE_TARGET = "{ID}"

internal class ImageIconUrlFactory(
    private val imageUrl: String
) {

    fun create(icon: String): String {
        return imageUrl.replace(REPLACE_TARGET, icon)
    }
}
