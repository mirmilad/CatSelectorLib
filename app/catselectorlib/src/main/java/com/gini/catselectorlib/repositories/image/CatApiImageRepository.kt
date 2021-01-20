package com.gini.catselectorlib.repositories.image

import com.gini.catselectorlib.api.ApiSchema
import com.gini.catselectorlib.models.ImageDto
import com.gini.catselectorlib.repositories.BaseRepository
import com.gini.catselectorlib.repositories.Resource

class CatApiImageRepository(
    private val apiClient: ApiSchema,
    private val catApiParams: GetAllPublicImagesParams,
    private val pageSize: Int
) : IImageRepository, BaseRepository() {

    override suspend fun getImages(page: Int): Resource<List<ImageDto>> {
        return apiRequest {
            apiClient.getAllPublicImages(
                catApiParams.apiKey,
                catApiParams.size,
                catApiParams.mimeTypes,
                catApiParams.order,
                catApiParams.categoryIds,
                catApiParams.breedId,
                pageSize,
                page
            )
        }
    }
}