package com.ghettowhitestar.magentatest.ui.gallery

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.ghettowhitestar.magentatest.api.PicsumApi
import com.ghettowhitestar.magentatest.data.PicsumPhoto
import com.ghettowhitestar.magentatest.db.LikedPhotoDao
import com.ghettowhitestar.magentatest.paginator.GalleryPagingSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PhotoRepository @Inject constructor(private val picsumApi: PicsumApi,private val likedPhotoDao: LikedPhotoDao) {

    fun getGalleryPhotosResult() =
        Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                maxSize = NETWORK_MAX_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { GalleryPagingSource(picsumApi) }
        ).liveData


     fun getLikesPhotoResult() =
        Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                maxSize = NETWORK_MAX_SIZE,
                enablePlaceholders = false
            )
        ){
            likedPhotoDao.getAllLikedPhotos()
        }.liveData

    suspend fun insertLikedPhoto(photo: PicsumPhoto) =
        likedPhotoDao.insertLikedPhoto(photo)

    suspend fun deleteLikedPhoto(photo: PicsumPhoto) =
        likedPhotoDao.deleteLikedPhoto(photo)


    companion object {
        private const val NETWORK_PAGE_SIZE = 20
        private const val NETWORK_MAX_SIZE = 100
    }
}