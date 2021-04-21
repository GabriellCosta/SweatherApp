package dev.tigrao.sweather.network.http.retry

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Timer
import java.util.concurrent.atomic.AtomicInteger
import kotlin.concurrent.schedule

private val RETRYABLE_RESPONSE_STATUS_CODE = arrayOf(500, 502, 503, 504)
private const val RETRY_DEFAULT_BACKOFF_SECONDS = 3
private const val MILLI_SECONDS_BY_SECOND = 1000L

internal class RetryCallback<T>(
    private val call: Call<T>,
    private val callback: Callback<T>,
    private val maxRetries: Int,
    private val retryCount: AtomicInteger = AtomicInteger(0)
) : Callback<T> {

    override fun onResponse(
        call: Call<T>,
        response: Response<T>
    ) {
        if (shouldRetryOnResponse(response)) {
            retry()
        } else {
            callback.onResponse(call, response)
        }
    }

    override fun onFailure(
        call: Call<T>,
        t: Throwable
    ) {
        if (shouldRetryOnFailure()) {
            retry()
        } else {
            callback.onFailure(call, t)
        }
    }

    private fun shouldRetryOnFailure() = retryCount.incrementAndGet() <= maxRetries

    private fun shouldRetryOnResponse(
        response: Response<T>
    ) = !response.isSuccessful && response.code() in RETRYABLE_RESPONSE_STATUS_CODE &&
        retryCount.incrementAndGet() <= maxRetries

    private fun retry() {
        Timer().schedule(calculateDelay()) {
            call.clone().enqueue(this@RetryCallback)
        }
    }

    private fun calculateDelay(): Long {
        return RETRY_DEFAULT_BACKOFF_SECONDS * MILLI_SECONDS_BY_SECOND
    }
}
