package dev.tigrao.sweather.domain.core

sealed class ResultError(val message: String) {
    data class NetworkError(
        val httpCode: Int,
        val httpMessage: String,
        val exceptionTitle: String,
        val localizeMessage: String,
        val isConnectionError: Boolean
    ) : ResultError(httpMessage)

    data class GenericError(
        val exceptionTitle: String,
        private val genericMessage: String,
        val isConnectionError: Boolean
    ) : ResultError(genericMessage)

    object UnknownError : ResultError("unknow")
}
