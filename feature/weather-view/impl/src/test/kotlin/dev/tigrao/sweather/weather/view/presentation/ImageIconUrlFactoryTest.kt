package dev.tigrao.sweather.weather.view.presentation

import org.junit.Assert.assertEquals
import org.junit.Test

class ImageIconUrlFactoryTest {

    private val subject = ImageIconUrlFactory("https://google.com/{ID}.png")

    @Test
    fun create_returnCorrectUrl() {
        val result = subject.create("01d")

        val expected = "https://google.com/01d.png"

        assertEquals(expected, result)
    }
}
