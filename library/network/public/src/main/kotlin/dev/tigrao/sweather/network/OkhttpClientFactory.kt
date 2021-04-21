package dev.tigrao.sweather.network

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

private const val CONNECTION_TIMEOUT = 1L
private const val READ_TIMEOUT = 30L
private const val WRITE_TIMEOUT = 15L

internal class OkhttpClientFactory(private val interceptors: List<Interceptor>) {

    fun createNewInstance(): OkHttpClient =
        OkHttpClient.Builder()
            .apply {
                interceptors.forEach(::addInterceptor)
            }
            .connectTimeout(CONNECTION_TIMEOUT, TimeUnit.MINUTES)
            .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
            .build()
}
