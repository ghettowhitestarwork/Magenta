package com.ghettowhitestar.magentatest.repo

import android.graphics.Bitmap
import android.os.Environment
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.ghettowhitestar.magentatest.api.PicsumApi
import com.ghettowhitestar.magentatest.data.PicsumPhoto
import com.ghettowhitestar.magentatest.db.LikedPhotoDao
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream


import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PhotoRepository @Inject constructor(private val picsumApi: PicsumApi,private val likedPhotoDao: LikedPhotoDao){

   fun getGalleryPhotosResult(pageSize:Int,currentPage: Int) = picsumApi.getListPhotos(currentPage,pageSize)




    fun getLikesPhotoResult() = likedPhotoDao.getAllLikedPhotos()


    fun insertLikedPhoto(photo: PicsumPhoto) =
        likedPhotoDao.insertLikedPhoto(photo)

    fun deleteLikedPhoto(photo: PicsumPhoto) =
        likedPhotoDao.deleteLikedPhoto(photo)


   fun deleteImage(photoName:String){

        val storageDir = File(
            Environment.getExternalStorageDirectory()
                .toString() + "/DCIM"
        )
        val imageFile = File(storageDir, photoName)
        if (imageFile.exists()){
            imageFile.delete()


        }
    }

    fun saveImage(image: Bitmap, photo: PicsumPhoto) {

        val storageDir = File(
            Environment.getExternalStorageDirectory()
                .toString() + "/DCIM"
        )
        var success = true
        if (!storageDir.exists()) {
            success = storageDir.mkdirs()
        }
        if (success) {
            val imageFile = File(storageDir, photo.path)
            try {
                val fOut: OutputStream = FileOutputStream(imageFile)
                image.compress(Bitmap.CompressFormat.JPEG, 30, fOut)
                fOut.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }


        }
    }


}