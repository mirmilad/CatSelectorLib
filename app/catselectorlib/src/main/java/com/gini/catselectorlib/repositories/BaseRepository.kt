package com.gini.catselectorlib.repositories

import retrofit2.Response

abstract class BaseRepository {

    companion object {
        val TAG = "BaseRepository"
    }

    suspend fun <T : Any> apiRequest(call: suspend () -> Response<T>): Resource<T> {

        val response = call.invoke()

        if (response.isSuccessful) {
            return Resource.success(response.body()!!)
        } else {
            val error = response.errorBody()?.string()?: "Unknown Error"
            val statusCode = response.code()
            return Resource.error(error, statusCode)
        }
    }
}