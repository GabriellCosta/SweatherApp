package dev.tigrao.sweather.domain.core

sealed class Result<out S, out E> {
    data class Success<out S>(val data: S) : Result<S, Nothing>()
    data class Error<E>(val errorResult: E) : Result<Nothing, E>()

    fun <T> map(success: (S) -> T, error: (E) -> T): T = when (this) {
        is Success -> success(data)
        is Error -> error(errorResult)
    }

    fun <T, V> transformMap(success: (S) -> T, error: (E) -> V): Result<T, V> = when (this) {
        is Success -> Success(success(data))
        is Error -> Error(error(errorResult))
    }

    fun onSuccess(block: (S) -> Unit): Result<S, E> {
        if (this is Success)
            block(this.data)

        return this
    }

    fun onError(block: (E) -> Unit): Result<S, E> {
        if (this is Error)
            block(this.errorResult)

        return this
    }
}
