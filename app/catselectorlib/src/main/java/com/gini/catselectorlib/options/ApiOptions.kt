package com.gini.catselectorlib.options

import java.io.Serializable

data class ApiOptions(var apiKey: String,
                      var size: String = DEFAULT_SIZE,
                      var mimeTypes: Array<String> = DEFAULT_MIME_TYPES,
                      var order: String = DEFAULT_ORDER,
                      var limit: Int = DEFAULT_LIMIT,
                      var categoryIds: Array<Int> = DEFAULT_CATEGORY_IDS,
                      var breedId: String = DEFAULT_BREED_ID) : Serializable {

    companion object {
        const val SIZE_FULL = "full"
        const val SIZE_MEDIUM = "med"
        const val SIZE_SMALL = "small"
        const val SIZE_THUMB = "thumb"
        const val SIZE_ALL = ""

        const val ORDER_RANDOM = "RANDOM"
        const val ORDER_ASC = "ASC"
        const val ORDER_DESC = "DESC"

        val DEFAULT_MIME_TYPES = emptyArray<String>()
        val DEFAULT_CATEGORY_IDS = emptyArray<Int>()
        const val DEFAULT_SIZE = SIZE_ALL
        const val DEFAULT_ORDER = ORDER_RANDOM
        const val DEFAULT_LIMIT = 50
        const val DEFAULT_BREED_ID = ""
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ApiOptions

        if (apiKey != other.apiKey) return false
        if (size != other.size) return false
        if (!mimeTypes.contentEquals(other.mimeTypes)) return false
        if (order != other.order) return false
        if (limit != other.limit) return false
        if (!categoryIds.contentEquals(other.categoryIds)) return false
        if (breedId != other.breedId) return false

        return true
    }

    override fun hashCode(): Int {
        var result = apiKey.hashCode()
        result = 31 * result + size.hashCode()
        result = 31 * result + mimeTypes.contentHashCode()
        result = 31 * result + order.hashCode()
        result = 31 * result + limit
        result = 31 * result + categoryIds.contentHashCode()
        result = 31 * result + breedId.hashCode()
        return result
    }
}