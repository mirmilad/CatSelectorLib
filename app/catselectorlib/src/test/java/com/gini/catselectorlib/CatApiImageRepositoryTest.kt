package com.gini.catselectorlib

import com.gini.catselectorlib.api.ApiSchema
import com.gini.catselectorlib.models.ImageDto
import com.gini.catselectorlib.repositories.Resource
import com.gini.catselectorlib.repositories.image.CatApiImageRepository
import com.gini.catselectorlib.repositories.image.GetAllPublicImagesParams
import com.nhaarman.mockitokotlin2.*
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertArrayEquals
import org.junit.Assert.assertEquals
import org.junit.Test
import retrofit2.Response

class CatApiImageRepositoryTest {


    private val apiParams =
        GetAllPublicImagesParams("API KEY", "", emptyArray(), "", emptyArray(), "")
    private val fakeApi = FakeApi()

    private val repository = CatApiImageRepository(fakeApi, apiParams, 3)

    /**
     * asserts that empty list works fine
     */
    @Test
    fun test_emptyList() {
        fakeApi.clear()
        runBlocking {
            val response = repository.getImages(0)
            assert(response is Resource.Success)
            assertEquals(response.data!!.size, 0)
        }
    }

    /**
     * asserts that a list w/ single item is loaded properly
     */
    @Test
    fun test_oneItem() {
        val image = ImageDto("1", "Url", 0, 0)
        fakeApi.clear()
        fakeApi.addImage(image)
        runBlocking {
            val response = repository.getImages(0)
            assert(response is Resource.Success)
            assertArrayEquals(response.data!!.toTypedArray(), arrayOf(image))
        }
    }

    /**
     * asserts loading a full list in multiple pages
     */
    @Test
    fun test_verifyCompleteList() {
        val images = (0..10).map { ImageDto(it.toString(), "Url", 0, 0) }
        fakeApi.clear()
        fakeApi.addImages(images)
        runBlocking {
            val finalResult = mutableListOf<ImageDto>()
            var page = 0
            do {
                val response = repository.getImages(page++)
                finalResult.addAll(response.data!!)
            } while (response.data!!.isNotEmpty())

            assertArrayEquals(finalResult.toTypedArray(), images.toTypedArray())
        }
    }

    /**
     * asserts the failure message when the initial load cannot complete
     */
    @Test
    fun test_failToLoad() {
        val error = "xxx"
        fakeApi.failureMsg = error
        runBlocking {
            val response = repository.getImages(0)
            assert(response is Resource.Error)
            assertEquals(response.message, error)
        }
    }

    @Test
    fun test_checkApiCall() {
        val apiMock = mock<ApiSchema> {
            onBlocking {
                getAllPublicImages(any(), any(), any(), any(), any(), any(), any(), any())
            } doReturn Response.success(emptyList())
        }
        val pageSize = 10
        val pageIndex = 5
        val repository = CatApiImageRepository(apiMock, apiParams, 10)
        runBlocking {
            repository.getImages(pageIndex)
            verify(apiMock, times(1))
                .getAllPublicImages(
                    apiParams.apiKey,
                    apiParams.size,
                    apiParams.mimeTypes,
                    apiParams.order,
                    apiParams.categoryIds,
                    apiParams.breedId,
                    pageSize,
                    pageIndex
                )
        }
    }

    @Test
    fun test_handleException() {
        val exception = RuntimeException("xxx")
        val apiMock = mock<ApiSchema> {
            onBlocking {
                getAllPublicImages(any(), any(), any(), any(), any(), any(), any(), any())
            } doThrow (exception)
        }
        val repository = CatApiImageRepository(apiMock, apiParams, 10)

        runBlocking {
            val response = repository.getImages(0)
            assert(response is Resource.Error)
            assertEquals(response.message, exception.message)
        }
    }
}