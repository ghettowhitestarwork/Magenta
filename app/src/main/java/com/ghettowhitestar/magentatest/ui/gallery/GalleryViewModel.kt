package com.ghettowhitestar.magentatest.ui.gallery

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel

class GalleryViewModel @ViewModelInject constructor(
    private val repository:GalleryRepository
) : ViewModel() {

}