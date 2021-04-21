package dev.tigrao.sweather.network.http.retry

import retrofit2.Call
import retrofit2.CallAdapter
import java.lang.reflect.Type

internal class RetryCallAdapter<R, T>(
    private val delegated: CallAdapter<R, T>,
    private val maxRetries: Int = 0,
) : CallAdapter<R, T> {

    override fun responseType(): Type = delegated.responseType()

    override fun adapt(call: Call<R>): T {
        val newCall = if (maxRetries > 0) RetryCall(call, maxRetries) else call

        return delegated.adapt(newCall)
    }
}
