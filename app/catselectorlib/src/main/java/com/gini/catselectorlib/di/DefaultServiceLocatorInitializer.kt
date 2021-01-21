package com.gini.catselectorlib.di

import android.content.Context
import com.gini.catselectorlib.utils.imageloader.GlideImageLoader
import com.gini.catselectorlib.utils.imageloader.IImageLoader

class DefaultServiceLocatorInitializer(context: Context) : ServiceLocator() {

    init {
        initialize(context)
        bindCustomServiceImplementation(IImageLoader::class.java, GlideImageLoader::class.java)
    }
}