package com.gini.catselectorlib

import android.content.Intent
import com.gini.catselectorlib.options.ApiOptions
import com.gini.catselectorlib.options.CatSelectorOptions
import com.gini.catselectorlib.options.UIOptions
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(manifest=Config.NONE, minSdk = 21, maxSdk = 28)

class CatSelectorIntentUnitTest {

    val testApiOptions = ApiOptions(
        "Test Api Key",
        ApiOptions.SIZE_FULL,
        arrayOf("image/jpeg", "image/png"),
        ApiOptions.ORDER_ASC,
        140,
        arrayOf(1, 2, 3),
        "Test Breed Id")
    val testUiOptions = UIOptions(
        "Test Title",
        "Button Text",
        100)
    val testOptions = CatSelectorOptions(testApiOptions, testUiOptions)

    val context =  RuntimeEnvironment.systemContext


    @Test
    fun test_apiKey() {
        val options = CatSelectorOptions(ApiOptions(testOptions.apiOptions.apiKey))
        val intent = CatSelectorIntent(context, options)
        val intentOptions = intent.getOptions()

        assertNotEquals(intentOptions, null)

        assertEquals(options.apiOptions.apiKey, testOptions.apiOptions.apiKey)
        assertEquals(options.apiOptions.breedId, ApiOptions.DEFAULT_BREED_ID)
        assertEquals(options.apiOptions.limit, ApiOptions.DEFAULT_LIMIT)
        assertEquals(options.apiOptions.order, ApiOptions.DEFAULT_ORDER)
        assertEquals(options.apiOptions.size, ApiOptions.DEFAULT_SIZE)
        assertArrayEquals(options.apiOptions.mimeTypes, ApiOptions.DEFAULT_MIME_TYPES)
        assertArrayEquals(options.apiOptions.categoryIds, ApiOptions.DEFAULT_CATEGORY_IDS)

        assertEquals(options.uiOptions.title, UIOptions.DEFAULT_TITLE)
        assertEquals(options.uiOptions.backButtonText, UIOptions.DEFAULT_BACK_BUTTON_TEXT)
        assertEquals(options.uiOptions.theme, UIOptions.DEFAULT_THEME)
    }

    @Test
    fun test_breedId() {
        val options = CatSelectorOptions(ApiOptions(testOptions.apiOptions.apiKey, breedId = testOptions.apiOptions.breedId))
        val intent = CatSelectorIntent(context, options)
        val intentOptions = intent.getOptions()

        assertNotEquals(intentOptions, null)

        assertEquals(options.apiOptions.apiKey, testOptions.apiOptions.apiKey)
        assertEquals(options.apiOptions.breedId, testOptions.apiOptions.breedId)
        assertEquals(options.apiOptions.limit, ApiOptions.DEFAULT_LIMIT)
        assertEquals(options.apiOptions.order, ApiOptions.DEFAULT_ORDER)
        assertEquals(options.apiOptions.size, ApiOptions.DEFAULT_SIZE)
        assertArrayEquals(options.apiOptions.mimeTypes, ApiOptions.DEFAULT_MIME_TYPES)
        assertArrayEquals(options.apiOptions.categoryIds, ApiOptions.DEFAULT_CATEGORY_IDS)

        assertEquals(options.uiOptions.title, UIOptions.DEFAULT_TITLE)
        assertEquals(options.uiOptions.backButtonText, UIOptions.DEFAULT_BACK_BUTTON_TEXT)
        assertEquals(options.uiOptions.theme, UIOptions.DEFAULT_THEME)
    }

    @Test
    fun test_limit() {
        val options = CatSelectorOptions(ApiOptions(testOptions.apiOptions.apiKey, limit = testOptions.apiOptions.limit))
        val intent = CatSelectorIntent(context, options)
        val intentOptions = intent.getOptions()

        assertNotEquals(intentOptions, null)

        assertEquals(options.apiOptions.apiKey, testOptions.apiOptions.apiKey)
        assertEquals(options.apiOptions.breedId, ApiOptions.DEFAULT_BREED_ID)
        assertEquals(options.apiOptions.limit, testOptions.apiOptions.limit)
        assertEquals(options.apiOptions.order, ApiOptions.DEFAULT_ORDER)
        assertEquals(options.apiOptions.size, ApiOptions.DEFAULT_SIZE)
        assertArrayEquals(options.apiOptions.mimeTypes, ApiOptions.DEFAULT_MIME_TYPES)
        assertArrayEquals(options.apiOptions.categoryIds, ApiOptions.DEFAULT_CATEGORY_IDS)

        assertEquals(options.uiOptions.title, UIOptions.DEFAULT_TITLE)
        assertEquals(options.uiOptions.backButtonText, UIOptions.DEFAULT_BACK_BUTTON_TEXT)
        assertEquals(options.uiOptions.theme, UIOptions.DEFAULT_THEME)
    }

    @Test
    fun test_order() {
        val options = CatSelectorOptions(ApiOptions(testOptions.apiOptions.apiKey, order = testOptions.apiOptions.order))
        val intent = CatSelectorIntent(context, options)
        val intentOptions = intent.getOptions()

        assertNotEquals(intentOptions, null)

        assertEquals(options.apiOptions.apiKey, testOptions.apiOptions.apiKey)
        assertEquals(options.apiOptions.breedId, ApiOptions.DEFAULT_BREED_ID)
        assertEquals(options.apiOptions.limit, ApiOptions.DEFAULT_LIMIT)
        assertEquals(options.apiOptions.order, testOptions.apiOptions.order)
        assertEquals(options.apiOptions.size, ApiOptions.DEFAULT_SIZE)
        assertArrayEquals(options.apiOptions.mimeTypes, ApiOptions.DEFAULT_MIME_TYPES)
        assertArrayEquals(options.apiOptions.categoryIds, ApiOptions.DEFAULT_CATEGORY_IDS)

        assertEquals(options.uiOptions.title, UIOptions.DEFAULT_TITLE)
        assertEquals(options.uiOptions.backButtonText, UIOptions.DEFAULT_BACK_BUTTON_TEXT)
        assertEquals(options.uiOptions.theme, UIOptions.DEFAULT_THEME)
    }

    @Test
    fun test_size() {
        val options = CatSelectorOptions(ApiOptions(testOptions.apiOptions.apiKey, size = testOptions.apiOptions.size))
        val intent = CatSelectorIntent(context, options)
        val intentOptions = intent.getOptions()

        assertNotEquals(intentOptions, null)

        assertEquals(options.apiOptions.apiKey, testOptions.apiOptions.apiKey)
        assertEquals(options.apiOptions.breedId, ApiOptions.DEFAULT_BREED_ID)
        assertEquals(options.apiOptions.limit, ApiOptions.DEFAULT_LIMIT)
        assertEquals(options.apiOptions.order, ApiOptions.DEFAULT_ORDER)
        assertEquals(options.apiOptions.size, testOptions.apiOptions.size)
        assertArrayEquals(options.apiOptions.mimeTypes, ApiOptions.DEFAULT_MIME_TYPES)
        assertArrayEquals(options.apiOptions.categoryIds, ApiOptions.DEFAULT_CATEGORY_IDS)

        assertEquals(options.uiOptions.title, UIOptions.DEFAULT_TITLE)
        assertEquals(options.uiOptions.backButtonText, UIOptions.DEFAULT_BACK_BUTTON_TEXT)
        assertEquals(options.uiOptions.theme, UIOptions.DEFAULT_THEME)
    }

    @Test
    fun test_mimeTypes() {
        val options = CatSelectorOptions(ApiOptions(testOptions.apiOptions.apiKey, mimeTypes = testOptions.apiOptions.mimeTypes))
        val intent = CatSelectorIntent(context, options)
        val intentOptions = intent.getOptions()

        assertNotEquals(intentOptions, null)

        assertEquals(options.apiOptions.apiKey, testOptions.apiOptions.apiKey)
        assertEquals(options.apiOptions.breedId, ApiOptions.DEFAULT_BREED_ID)
        assertEquals(options.apiOptions.limit, ApiOptions.DEFAULT_LIMIT)
        assertEquals(options.apiOptions.order, ApiOptions.DEFAULT_ORDER)
        assertEquals(options.apiOptions.size, ApiOptions.DEFAULT_SIZE)
        assertArrayEquals(options.apiOptions.mimeTypes, testOptions.apiOptions.mimeTypes)
        assertArrayEquals(options.apiOptions.categoryIds, ApiOptions.DEFAULT_CATEGORY_IDS)

        assertEquals(options.uiOptions.title, UIOptions.DEFAULT_TITLE)
        assertEquals(options.uiOptions.backButtonText, UIOptions.DEFAULT_BACK_BUTTON_TEXT)
        assertEquals(options.uiOptions.theme, UIOptions.DEFAULT_THEME)
    }

    @Test
    fun test_categoryIds() {
        val options = CatSelectorOptions(ApiOptions(testOptions.apiOptions.apiKey, categoryIds = testOptions.apiOptions.categoryIds))
        val intent = CatSelectorIntent(context, options)
        val intentOptions = intent.getOptions()

        assertNotEquals(intentOptions, null)

        assertEquals(options.apiOptions.apiKey, testOptions.apiOptions.apiKey)
        assertEquals(options.apiOptions.breedId, ApiOptions.DEFAULT_BREED_ID)
        assertEquals(options.apiOptions.limit, ApiOptions.DEFAULT_LIMIT)
        assertEquals(options.apiOptions.order, ApiOptions.DEFAULT_ORDER)
        assertEquals(options.apiOptions.size, ApiOptions.DEFAULT_SIZE)
        assertArrayEquals(options.apiOptions.mimeTypes, ApiOptions.DEFAULT_MIME_TYPES)
        assertArrayEquals(options.apiOptions.categoryIds, testOptions.apiOptions.categoryIds)

        assertEquals(options.uiOptions.title, UIOptions.DEFAULT_TITLE)
        assertEquals(options.uiOptions.backButtonText, UIOptions.DEFAULT_BACK_BUTTON_TEXT)
        assertEquals(options.uiOptions.theme, UIOptions.DEFAULT_THEME)
    }

    @Test
    fun test_title() {
        val options = CatSelectorOptions(ApiOptions(testOptions.apiOptions.apiKey), UIOptions(title = testOptions.uiOptions.title))
        val intent = CatSelectorIntent(context, options)
        val intentOptions = intent.getOptions()

        assertNotEquals(intentOptions, null)

        assertEquals(options.apiOptions.apiKey, testOptions.apiOptions.apiKey)
        assertEquals(options.apiOptions.breedId, ApiOptions.DEFAULT_BREED_ID)
        assertEquals(options.apiOptions.limit, ApiOptions.DEFAULT_LIMIT)
        assertEquals(options.apiOptions.order, ApiOptions.DEFAULT_ORDER)
        assertEquals(options.apiOptions.size, ApiOptions.DEFAULT_SIZE)
        assertArrayEquals(options.apiOptions.mimeTypes, ApiOptions.DEFAULT_MIME_TYPES)
        assertArrayEquals(options.apiOptions.categoryIds, ApiOptions.DEFAULT_CATEGORY_IDS)

        assertEquals(options.uiOptions.title, testOptions.uiOptions.title)
        assertEquals(options.uiOptions.backButtonText, UIOptions.DEFAULT_BACK_BUTTON_TEXT)
        assertEquals(options.uiOptions.theme, UIOptions.DEFAULT_THEME)
    }

    @Test
    fun test_theme() {
        val options = CatSelectorOptions(ApiOptions(testOptions.apiOptions.apiKey), UIOptions(theme = testOptions.uiOptions.theme))
        val intent = CatSelectorIntent(context, options)
        val intentOptions = intent.getOptions()

        assertNotEquals(intentOptions, null)

        assertEquals(options.apiOptions.apiKey, testOptions.apiOptions.apiKey)
        assertEquals(options.apiOptions.breedId, ApiOptions.DEFAULT_BREED_ID)
        assertEquals(options.apiOptions.limit, ApiOptions.DEFAULT_LIMIT)
        assertEquals(options.apiOptions.order, ApiOptions.DEFAULT_ORDER)
        assertEquals(options.apiOptions.size, ApiOptions.DEFAULT_SIZE)
        assertArrayEquals(options.apiOptions.mimeTypes, ApiOptions.DEFAULT_MIME_TYPES)
        assertArrayEquals(options.apiOptions.categoryIds, ApiOptions.DEFAULT_CATEGORY_IDS)

        assertEquals(options.uiOptions.title, UIOptions.DEFAULT_TITLE)
        assertEquals(options.uiOptions.backButtonText, UIOptions.DEFAULT_BACK_BUTTON_TEXT)
        assertEquals(options.uiOptions.theme, testOptions.uiOptions.theme)
    }

    @Test
    fun test_backButtonText() {
        val options = CatSelectorOptions(ApiOptions(testOptions.apiOptions.apiKey), UIOptions(backButtonText = testOptions.uiOptions.backButtonText))
        val intent = CatSelectorIntent(context, options)
        val intentOptions = intent.getOptions()

        assertNotEquals(intentOptions, null)

        assertEquals(options.apiOptions.apiKey, testOptions.apiOptions.apiKey)
        assertEquals(options.apiOptions.breedId, ApiOptions.DEFAULT_BREED_ID)
        assertEquals(options.apiOptions.limit, ApiOptions.DEFAULT_LIMIT)
        assertEquals(options.apiOptions.order, ApiOptions.DEFAULT_ORDER)
        assertEquals(options.apiOptions.size, ApiOptions.DEFAULT_SIZE)
        assertArrayEquals(options.apiOptions.mimeTypes, ApiOptions.DEFAULT_MIME_TYPES)
        assertArrayEquals(options.apiOptions.categoryIds, ApiOptions.DEFAULT_CATEGORY_IDS)

        assertEquals(options.uiOptions.title, UIOptions.DEFAULT_TITLE)
        assertEquals(options.uiOptions.backButtonText, testOptions.uiOptions.backButtonText)
        assertEquals(options.uiOptions.theme, UIOptions.DEFAULT_THEME)
    }

    @Test
    fun test_castToIntent() {
        val options = testOptions
        val catSelectorIntent = CatSelectorIntent(context, options)
        val intent = catSelectorIntent as Intent

        val extraOptions = intent.getCatSelectorOptionsExtra()

        assertEquals(options, extraOptions!!)
    }

    @Test
    fun test_normalIntent() {
        val options = testOptions
        val intent = Intent()
        intent.putCatSelectorOptionsExtra(options)
        val extraOptions = intent.getCatSelectorOptionsExtra()

        assertEquals(options, extraOptions!!)
    }
}