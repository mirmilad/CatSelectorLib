package com.gini.catselectorlib.repositories

import retrofit2.Response

abstract class BaseRepository {

    suspend fun <T : Any> apiRequest(call: suspend () -> Response<T>): Resource<T> {
        try {
            val response = call.invoke()

            if (response.isSuccessful) {
                return Resource.success(response.body()!!)
            } else {
                val error = response.errorBody()?.string() ?: "Unknown Error"
                val statusCode = response.code()
                return Resource.error(error, statusCode)
            }
        } catch (e: Exception) {
            return Resource.error(e.message!!, -1)
        }
    }
}