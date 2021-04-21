package dev.tigrao.sweather.domain.core.api

import dev.tigrao.sweather.domain.core.Result
import dev.tigrao.sweather.domain.core.ResultError
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.internal.http2.ConnectionShutdownException
import retrofit2.HttpException
import java.net.ConnectException
import java.net.UnknownHostException

suspend fun <T> callApi(
    dispatcher: CoroutineDispatcher = Dispatchers.IO,
    apiCall: suspend () -> T
): Result<T, ResultError> {
    return withContext(dispatcher) {
        try {
            Result.Success(apiCall())
        } catch (@Suppress("TooGenericExceptionCaught") ex: Exception) {
            val mappedException: ResultError = when (ex) {
                is HttpException -> mapException(ex)
                else -> mapException(ex)
            }

            Result.Error(mappedException)
        }
    }
}

private fun mapException(httpException: HttpException): ResultError.NetworkError {
    return ResultError.NetworkError(
        httpCode = httpException.code(),
        httpMessage = httpException.message(),
        localizeMessage = httpException.localizedMessage,
        exceptionTitle = httpException.javaClass.name,
        isConnectionError = false
    )
}

private fun mapException(exception: Exception): ResultError.GenericError {
    return ResultError.GenericError(
        genericMessage = exception.message ?: "",
        exceptionTitle = exception.javaClass.name,
        isConnectionError = exception.isConnectionError()
    )
}

private fun Throwable.isConnectionError() =
    this is ConnectException || this is UnknownHostException || this is ConnectionShutdownException
