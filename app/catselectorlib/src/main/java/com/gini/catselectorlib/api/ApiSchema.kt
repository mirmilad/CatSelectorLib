package com.gini.catselectorlib.api

import com.gini.catselectorlib.models.ImageDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ApiSchema {
    @GET("v1/images/search?format=json")
    suspend fun getAllPublicImages(
        @Header("x-api-key") apiKey: String,
        @Query("size") size: String,
        @Query("mime_types") mimeTypes: Array<String>,
        @Query("order") order: String,
        @Query("category_ids") categoryIds: Array<Int>,
        @Query("breed_id") breedId: String,
        @Query("limit") limit: Int,
        @Query("page") page: Int
    ): Response<List<ImageDto>>
}