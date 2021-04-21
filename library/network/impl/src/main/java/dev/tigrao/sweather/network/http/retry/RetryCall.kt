package dev.tigrao.sweather.network.http.retry

import okhttp3.Request
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

internal class RetryCall<R>(
    private val delegated: Call<R>,
    private val maxRetries: Int,
) : Call<R> {
    override fun enqueue(callback: Callback<R>) {
        delegated.enqueue(RetryCallback(delegated, callback, maxRetries))
    }

    override fun isExecuted(): Boolean {
        return delegated.isExecuted
    }

    override fun clone(): Call<R> {
        return RetryCall(delegated.clone(), maxRetries)
    }

    override fun isCanceled(): Boolean {
        return delegated.isCanceled
    }

    override fun cancel() {
        delegated.cancel()
    }

    override fun execute(): Response<R> {
        return delegated.execute()
    }

    override fun request(): Request {
        return delegated.request()
    }

    override fun timeout(): Timeout {
        return delegated.timeout()
    }
}
