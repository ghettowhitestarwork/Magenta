package com.ghettowhitestar.magentatest.ui.gallery

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.ghettowhitestar.magentatest.api.PicsumApi
import com.ghettowhitestar.magentatest.ui.gallery.paginator.GalleryPagingSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GalleryRepository @Inject constructor(private val picsumApi: PicsumApi) {

    fun getPhotosResult() =
        Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                maxSize = NETWORK_MAX_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { GalleryPagingSource(picsumApi) }
        ).liveData

    companion object {
        private const val NETWORK_PAGE_SIZE = 20
        private const val NETWORK_MAX_SIZE = 100
    }
}