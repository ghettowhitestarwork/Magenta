package com.ghettowhitestar.magentatest.ui.gallery

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn

class GalleryViewModel @ViewModelInject constructor(
    private val repository:GalleryRepository
) : ViewModel() {
        val photos = repository.getPhotosResult().cachedIn(viewModelScope)
}