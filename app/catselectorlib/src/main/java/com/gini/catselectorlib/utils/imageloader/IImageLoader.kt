package com.gini.catselectorlib.utils.imageloader

import android.widget.ImageView

interface IImageLoader {
    fun load(url: String, target: ImageView)
}