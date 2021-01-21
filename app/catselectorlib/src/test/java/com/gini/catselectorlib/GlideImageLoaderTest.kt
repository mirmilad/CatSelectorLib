package com.gini.catselectorlib

import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.RequestManager
import com.gini.catselectorlib.utils.imageloader.GlideImageLoader
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import org.junit.Test
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.mockStatic

class GlideImageLoaderTest {

    private val context = mock<Context> {
        on {
            applicationContext
        } doReturn this.mock
    }

    private val requestBuilder = mock<RequestBuilder<Drawable>>()
    private val requestManager = mock<RequestManager> {
        on { load(any(String::class.java)) } doReturn requestBuilder
    }
    private val imageView = mock<ImageView>()

    init {
        mockStatic(Glide::class.java).apply {
            whenever(Glide.with(any(Context::class.java))).thenReturn(requestManager)
        }
    }

    @Test
    fun test_loadImage() {
        val glideImageLoader = GlideImageLoader(context)
        val url = "xxx"
        glideImageLoader.load(url, imageView)
        verify(requestManager).load(url)
        verify(requestBuilder).into(imageView)
    }
}