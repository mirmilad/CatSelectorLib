package com.gini.catselectorlib.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gini.catselectorlib.repositories.image.IImageRepository
import com.gini.catselectorlib.utils.imageloader.IImageDownloader

class CatSelectorViewModelFactory(private val imageRepository: IImageRepository,
                                  private val pageSize: Int,
                                  private val imageDownloader: IImageDownloader,
                                  private val downloadPath: String): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CatSelectorViewModel(imageRepository, pageSize, imageDownloader, downloadPath) as T
    }
}