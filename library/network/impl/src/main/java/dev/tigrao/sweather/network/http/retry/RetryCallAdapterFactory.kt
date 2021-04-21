package dev.tigrao.sweather.network.http.retry

import dev.tigrao.sweather.network.http.Retry
import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.Type

private const val MAX_TRIES = 0

internal class RetryCallAdapterFactory() : CallAdapter.Factory() {
    override fun get(
        returnType: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *> {
        val retryQuantity = getRetryQuantity(annotations)?.max ?: MAX_TRIES

        return RetryCallAdapter(
            retrofit.nextCallAdapter(this, returnType, annotations),
            retryQuantity,
        )
    }

    private fun getRetryQuantity(annotations: Array<Annotation>) =
        annotations.filterIsInstance<Retry>().firstOrNull()
}
