package com.gini.catselectorlib

import android.content.Context
import com.gini.catselectorlib.di.DefaultServiceLocatorInitializer
import com.gini.catselectorlib.di.ServiceLocator
import com.gini.catselectorlib.utils.imageloader.GlideImageLoader
import com.gini.catselectorlib.utils.imageloader.IImageLoader
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import org.junit.Test

class DefaultServiceLocatorInitializerTest {

    private val context = mock<Context> {
        on {
            applicationContext
        } doReturn this.mock
    }

    @Test
    fun test_defaultServices() {
        DefaultServiceLocatorInitializer(context)

        val imageLoader = ServiceLocator.get(IImageLoader::class.java)
        assert(imageLoader is GlideImageLoader)
    }
}