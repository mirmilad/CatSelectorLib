package com.gini.catselectorlib.repositories.image

import com.gini.catselectorlib.models.ImageDto
import com.gini.catselectorlib.repositories.Resource

interface IImageRepository {
    suspend fun getImages(page: Int): Resource<List<ImageDto>>
}