package com.ghettowhitestar.magentatest.ui.gallery

import com.ghettowhitestar.magentatest.api.PicsumApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GalleryRepository @Inject constructor(private val picsumApi: PicsumApi) {
}