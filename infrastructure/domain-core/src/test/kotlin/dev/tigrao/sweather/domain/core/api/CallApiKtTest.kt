package dev.tigrao.sweather.domain.core.api

import dev.tigrao.sweather.domain.core.Result
import dev.tigrao.sweather.domain.core.ResultError
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType
import okhttp3.ResponseBody
import okhttp3.internal.http2.ConnectionShutdownException
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response
import java.net.ConnectException
import java.net.UnknownHostException

class CallApiKtTest {

    @Test
    fun callApi_withSuccess_returnSuccessResult() = runBlocking {
        val expected = "String Object"

        val result = callApi { expected }

        assertTrue(result is Result.Success<String>)
    }

    @Test
    fun callApi_withSuccess_returnReturnCorrectValue() = runBlocking {
        val expected = "String Object"

        val result = callApi { expected }

        result as Result.Success

        assertEquals(expected, result.data)
    }

    @Test
    fun callApi_withError_returnReturnResultError() = runBlocking {
        val expected = "String Object"

        val result = callApi { throw IllegalArgumentException(expected) }

        assertTrue(result is Result.Error)
    }

    @Test
    fun callApi_withError_returnReturnResultErrorWithMappedMessage() = runBlocking {
        val result = callApi { throw IllegalArgumentException("String Object") }

        result as Result.Error

        val expected = ResultError.GenericError(
            exceptionTitle = "java.lang.IllegalArgumentException",
            genericMessage = "String Object",
            isConnectionError = false,
        )

        assertEquals(expected, result.errorResult)
    }

    @Test
    fun callApi_withErrorHttpException_returnReturnResultErrorWithMappedMessage() = runBlocking {
        val response = Response.error<String>(
            404,
            ResponseBody.create(
                MediaType.parse("txt"),
                "sample"
            )
        )

        val result = callApi { throw HttpException(response) }

        result as Result.Error

        val expected = ResultError.NetworkError(
            httpCode = 404,
            httpMessage = "Response.error()",
            localizeMessage = "HTTP 404 Response.error()",
            exceptionTitle = "retrofit2.HttpException",
            isConnectionError = false,
        )

        assertEquals(expected, result.errorResult)
    }

    @Test
    fun callApe_withErrorConnectException_returnReturnResultErrorWithMappedMessage() = runBlocking {
        val result = callApi { throw ConnectException() }

        result as Result.Error

        val expected = ResultError.GenericError(
            exceptionTitle = "java.net.ConnectException",
            genericMessage = "",
            isConnectionError = true,
        )

        assertEquals(expected, result.errorResult)
    }

    @Test
    fun callApe_withErrorUnknownHostException_returnReturnResultErrorWithMappedMessage() = runBlocking {
        val result = callApi { throw UnknownHostException() }

        result as Result.Error

        val expected = ResultError.GenericError(
            exceptionTitle = "java.net.UnknownHostException",
            genericMessage = "",
            isConnectionError = true,
        )

        assertEquals(expected, result.errorResult)
    }

    @Test
    fun callApe_withErrorConnectionShutdownException_returnReturnResultErrorWithMappedMessage() = runBlocking {
        val result = callApi { throw ConnectionShutdownException() }

        result as Result.Error

        val expected = ResultError.GenericError(
            exceptionTitle = "okhttp3.internal.http2.ConnectionShutdownException",
            genericMessage = "",
            isConnectionError = true,
        )

        assertEquals(expected, result.errorResult)
    }
}
