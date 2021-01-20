package com.gini.catselectorlib.repositories.image

data class GetAllPublicImagesParams(
    val apiKey: String,
    val size: String,
    val mimeTypes: Array<String>,
    val order: String,
    val categoryIds: Array<Int>,
    val breedId: String
)