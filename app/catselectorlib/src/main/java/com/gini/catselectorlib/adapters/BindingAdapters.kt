package com.gini.catselectorlib.adapters

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.gini.catselectorlib.di.ServiceLocator
import com.gini.catselectorlib.utils.imageloader.IImageLoader


@BindingAdapter("imageUrl")
fun loadImage(imageView: ImageView, url: String) {
    val imageLoaderService = ServiceLocator.get(IImageLoader::class.java)
    imageLoaderService.load(url, imageView)
}
