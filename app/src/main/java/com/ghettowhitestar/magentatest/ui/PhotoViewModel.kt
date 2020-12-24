package com.ghettowhitestar.magentatest.ui

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Environment
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.ghettowhitestar.magentatest.R
import com.ghettowhitestar.magentatest.data.PicsumPhoto
import com.ghettowhitestar.magentatest.ui.gallery.PhotoRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

class PhotoViewModel @ViewModelInject constructor(
    private val repository: PhotoRepository
) : ViewModel() {
        val photos = repository.getGalleryPhotosResult().cachedIn(viewModelScope)
        val likedPhotos = repository.getLikesPhotoResult().cachedIn(viewModelScope)

    fun likePhoto(photo: PicsumPhoto,bitmap: Bitmap){
            if (photo.isLikedPhoto){
                photo.isLikedPhoto = false
                CoroutineScope(Dispatchers.IO).launch {
                    deleteImage(photo.path)
                    repository.deleteLikedPhoto(photo)
                }
            }else {
                photo.isLikedPhoto = true
                photo.path = photo.id + ".jpg"
                CoroutineScope(Dispatchers.IO).launch {
                    saveImage(bitmap,photo)
                    repository.insertLikedPhoto(photo)
                }
        }
    }


    private fun deleteImage(photoName:String){

            val storageDir = File(
                Environment.getExternalStorageDirectory()
                    .toString() + "/DCIM"
            )
            val imageFile = File(storageDir, photoName)
            if (imageFile.exists()){
                imageFile.delete()


        }
    }
    private fun saveImage(image: Bitmap, photo: PicsumPhoto) {

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