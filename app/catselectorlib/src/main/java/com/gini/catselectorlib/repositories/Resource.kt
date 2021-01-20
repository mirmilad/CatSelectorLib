package com.gini.catselectorlib.repositories

sealed class Resource<out T>(
    open val statusCode: Int,
    open val data: T?,
    open val message: String?
) {

    data class Success<out T>(override val data: T?) : Resource<T>(200, data, null)
    data class Error<out T>(
        override val message: String,
        override val statusCode: Int,
        override val data: T? = null
    ) : Resource<T>(statusCode, data, message)

    companion object {
        fun <T> success(data: T): Resource<T> {
            return Success(data)
        }

        fun <T> error(message: String, statusCode: Int, data: T? = null): Resource<T> {
            return Error(message, statusCode, data)
        }
    }
}