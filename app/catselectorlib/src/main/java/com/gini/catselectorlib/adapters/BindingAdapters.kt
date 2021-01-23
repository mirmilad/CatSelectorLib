package com.gini.catselectorlib.adapters

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.gini.catselectorlib.di.ServiceLocator
import com.gini.catselectorlib.utils.imageloader.IImageLoader


@BindingAdapter("imageUrl")
fun loadImage(imageView: ImageView, url: String) {
    val imageLoaderService = ServiceLocator.get(IImageLoader::class.java)
    imageLoaderService.load(url, imageView)
}

@BindingAdapter("invisible")
fun invisibleView(view: View, invisible: Boolean) {
    view.visibility = if(invisible) View.INVISIBLE else View.VISIBLE
}