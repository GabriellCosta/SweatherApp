package dev.tigrao.sweather.network.interceptor

import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import okhttp3.*
import org.junit.Assert.assertEquals
import org.junit.Test
import java.net.HttpURLConnection

class ApiKeyInterceptorTest {

    private val token = "mock-tocken"
    private val subject = ApiKeyInterceptor(token)
    val chain = mockk<Interceptor.Chain>()
    private val requestAfterInterceptor = slot<Request>()

    @Test
    fun intercept_verifyHasApiToken() {
        prepareScenario()

        subject.intercept(chain)

        assertEquals(token, requestAfterInterceptor.captured.url.queryParameter("appid"))
    }

    private fun prepareScenario(
        host: String = "google.com.br",
    ) {
        val requestBuilder = Request.Builder().url(createHttpUrl(host).build())
        val request = requestBuilder.build()

        every { chain.request() } answers { request }
        every { chain.proceed(capture(requestAfterInterceptor)) } answers { createResponse(request) }
        every { chain.request().url.host } answers { request.url.host }
        every { chain.request().url.newBuilder() } answers { createHttpUrl(host) }
        every { chain.request().newBuilder() } answers { requestBuilder }
    }

    private fun createResponse(request: Request) = Response.Builder()
        .request(request)
        .code(HttpURLConnection.HTTP_OK)
        .message("wololo message")
        .protocol(Protocol.HTTP_2)
        .build()

    private fun createHttpUrl(host: String) =
        HttpUrl.Builder()
            .scheme("https")
            .host(host)
}
