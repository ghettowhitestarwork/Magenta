package com.ghettowhitestar.magentatest.repo

import android.graphics.Bitmap
import android.net.ConnectivityManager
import android.os.Environment
import com.ghettowhitestar.magentatest.api.PicsumApi
import com.ghettowhitestar.magentatest.data.PicsumPhoto
import com.ghettowhitestar.magentatest.db.LikedPhotoDao
import com.ghettowhitestar.magentatest.source.CacheManager
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream


import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PhotoRepository @Inject constructor(
    private val picsumApi: PicsumApi,
    private val likedPhotoDao: LikedPhotoDao,
    private val connectivityManager: ConnectivityManager,
    private val cacheManager: CacheManager
) {

    fun getGalleryPhotosResult(pageSize: Int, currentPage: Int) =
        picsumApi.getListGalleryPhotos(currentPage, pageSize)

    fun getLikesPhotoResult() =
        likedPhotoDao.getAllLikedPhotos()

    fun insertLikedPhoto(photo: PicsumPhoto) =
        likedPhotoDao.insertLikedPhoto(photo)

    fun deleteLikedPhoto(photo: PicsumPhoto) =
        likedPhotoDao.deleteLikedPhoto(photo)

    fun isNetworkAvailable() = connectivityManager.activeNetwork == null

    fun saveImage(image: Bitmap, photo: PicsumPhoto) = cacheManager.saveImage(image, photo)

    fun deleteImage(path: String) = cacheManager.deleteImage(path)
}