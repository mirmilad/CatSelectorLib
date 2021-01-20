package com.gini.catselectorlib.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gini.catselectorlib.models.ImageDto
import com.gini.catselectorlib.repositories.Resource
import com.gini.catselectorlib.repositories.image.IImageRepository
import kotlinx.coroutines.launch

class CatSelectorViewModel(
    private val imageRepository: IImageRepository,
    private val pageSize: Int
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
    private var hasMoreData = true

    val images: LiveData<List<ImageDto>> = _images
    val loading: LiveData<Boolean> = _loading
    val error: LiveData<String> = _error


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

}