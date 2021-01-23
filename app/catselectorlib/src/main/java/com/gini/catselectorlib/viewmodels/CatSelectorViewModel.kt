package com.gini.catselectorlib.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gini.catselectorlib.models.ImageDto
import com.gini.catselectorlib.repositories.Resource
import com.gini.catselectorlib.repositories.image.IImageRepository
import com.gini.catselectorlib.utils.imageloader.IImageDownloader
import kotlinx.coroutines.launch
import java.io.File
import java.lang.Exception

class CatSelectorViewModel(
    private val imageRepository: IImageRepository,
    private val pageSize: Int,
    private val imageDownloader: IImageDownloader,
    private val downloadPath: String,
) : ViewModel() {

    private val _images = object : MutableLiveData<List<ImageDto>>() {
        override fun onActive() {
            super.onActive()
            if ((this.value ?: emptyList()).isEmpty()) {
                loadNewPage()
            }
        }
    }
    private val _loading = MutableLiveData(false)
    private val _error = MutableLiveData<String>()
    private val _downloading = MutableLiveData(false)
    private val _image = MutableLiveData<File>()
    private var hasMoreData = true

    val images: LiveData<List<ImageDto>> = _images
    val loading: LiveData<Boolean> = _loading
    val error: LiveData<String> = _error
    val downloading: LiveData<Boolean> = _downloading
    val image: LiveData<File> = _image

    fun loadNewPage() {
        if (this._loading.value == true || !hasMoreData)
            return

        this._loading.value = true
        viewModelScope.launch {
            val pageIndex = (_images.value?.size ?: 0) / pageSize
            val response = imageRepository.getImages(pageIndex)
            _loading.value = false
            when (response) {
                is Resource.Success -> {
                    if ((response.data ?: emptyList()).isNotEmpty()) {
                        _images.value = (_images.value ?: emptyList<ImageDto>()) + response.data!!
                    }
                    if ((response.data?.size ?: 0) < pageSize) {
                        hasMoreData = false
                    }
                }
                is Resource.Error -> {
                    _error.value = response.message
                }
            }
        }
    }

    fun downloadImage(image: ImageDto) {
        if(_downloading.value == true)
            return

        viewModelScope.launch {
            _downloading.value = true
            try {
                _image.value = imageDownloader.download(image.url, downloadPath)
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _downloading.value = false
            }
        }
    }
}