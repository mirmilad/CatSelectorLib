package com.gini.catselectorlib

import android.widget.ImageView
import com.gini.catselectorlib.adapters.loadImage
import com.gini.catselectorlib.di.ServiceLocator
import com.gini.catselectorlib.utils.imageloader.IImageLoader
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import org.junit.Test

class BindingAdaptersTest {

    @Test
    fun test_loadImage() {
        val imageLoader = mock<IImageLoader>()
        ServiceLocator.bindCustomServiceInstance(IImageLoader::class.java, imageLoader)
        val imageView = mock<ImageView>()
        val url = "xxx"
        loadImage(imageView, url)
        verify(imageLoader).load(url, imageView)
    }
}