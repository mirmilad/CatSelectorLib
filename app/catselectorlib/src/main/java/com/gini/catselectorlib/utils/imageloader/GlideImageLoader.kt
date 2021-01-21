package com.gini.catselectorlib.utils.imageloader

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.gini.catselectorlib.di.ServiceLocator

class GlideImageLoader(private val context: Context) : IImageLoader, ServiceLocator.IService {

    override fun load(url: String, target: ImageView) {
        Glide.with(context)
            .load(url)
            .into(target)
    }

}