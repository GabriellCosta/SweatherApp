package dev.tigrao.sweather.network.interceptor

import dev.tigrao.sweather.network.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

private const val API_KEY_PARAM = "appid"

internal class ApiKeyInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var original = chain.request()
        val token = BuildConfig.API_TOKEN
        val url = original.url.newBuilder().addQueryParameter(API_KEY_PARAM, token).build()
        original = original.newBuilder().url(url).build()
        return chain.proceed(original)
    }
}
