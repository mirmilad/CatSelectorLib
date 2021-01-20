package com.gini.catselectorlib

import com.gini.catselectorlib.api.ApiSchema
import com.gini.catselectorlib.models.ImageDto
import okhttp3.ResponseBody
import retrofit2.Response

class FakeApi : ApiSchema {

    private val model = mutableListOf<ImageDto>()
    var failureMsg: String? = null

    fun addImage(image: ImageDto) {
        model.add(image)
    }

    fun addImages(images: Collection<ImageDto>) {
        model.addAll(images)
    }

    fun clear() {
        model.clear()
    }

    override suspend fun getAllPublicImages(
        apiKey: String,
        size: String,
        mimeTypes: Array<String>,
        order: String,
        categoryIds: Array<Int>,
        breedId: String,
        limit: Int,
        page: Int
    ): Response<List<ImageDto>> {
        failureMsg?.let {
            return Response.error(500, ResponseBody.create(null, it))
        }

        val startIndex = page * limit
        if (startIndex > this.model.size)
            return Response.success(emptyList())

        val endIndex = Math.min(startIndex + limit, this.model.size)
        return Response.success(this.model.subList(startIndex, endIndex))
    }

}