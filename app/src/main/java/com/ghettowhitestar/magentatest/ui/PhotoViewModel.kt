package com.ghettowhitestar.magentatest.ui

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Environment
import android.os.Handler
import android.os.Message
import android.util.Log
import android.util.TimeUtils
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.ghettowhitestar.magentatest.R
import com.ghettowhitestar.magentatest.data.PicsumPhoto
import com.ghettowhitestar.magentatest.paginator.NetworkResponceState
import com.ghettowhitestar.magentatest.paginator.Pageable
import com.ghettowhitestar.magentatest.paginator.Status
import com.ghettowhitestar.magentatest.ui.gallery.PhotoRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

class PhotoViewModel @ViewModelInject constructor(
    private val repository: PhotoRepository
) : ViewModel(),Pageable {
       /* val photos = repository.getGalleryPhotosResult().cachedIn(viewModelScope)
        var likedPhotos = repository.getLikesPhotoResult().cachedIn(viewModelScope)
*/
    override val pageSize: Int = 30
    override var hasMore: Boolean = true
    override var currentPage: Int = 0
    override var isDownloading: Boolean = false
    private val _res = MutableLiveData<NetworkResponceState<MutableList<PicsumPhoto>>>()

    val res : LiveData<NetworkResponceState<MutableList<PicsumPhoto>>>
        get() = _res

    init {
        getEmployees()
    }

    private fun getEmployees() {
        isDownloading = true
        Log.e("TAG", "$isDownloading")
        viewModelScope.launch {
            _res.postValue(NetworkResponceState.loading(null))
            repository.getGalleryPhotosResult(pageSize,currentPage).let {
                if (it.isSuccessful) {
                    currentPage++
                    Log.e("TAG", "$currentPage")
                    _res.postAdd(NetworkResponceState.success(it.body()))
                    Thread.sleep(2500)
                } else {
                    _res.postValue(NetworkResponceState.error(it.errorBody().toString(), null))
                }
                isDownloading = false
            }
        }
        }

    fun <T> MutableLiveData<NetworkResponceState<MutableList<T>>>.postAdd(networkResponceState: NetworkResponceState<List<T>>) {
        networkResponceState.data?.let {
            val updatedValue = NetworkResponceState(
                networkResponceState.status,
                (this@postAdd.value?.data ?: mutableListOf<T>()).apply {
                    addAll(it)
                },
                networkResponceState.message
            )
            postValue(updatedValue)
        }
    }

    fun likePhoto(position:Int,photo: PicsumPhoto,bitmap: Bitmap){
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

    override fun loadNextPage() {
        getEmployees()
    }


}