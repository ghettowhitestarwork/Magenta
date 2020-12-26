package com.ghettowhitestar.magentatest.vm

import android.annotation.SuppressLint
import android.graphics.Bitmap
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ghettowhitestar.magentatest.data.PicsumPhoto
import com.ghettowhitestar.magentatest.paginator.Pageable
import com.ghettowhitestar.magentatest.repo.PhotoRepository
import io.reactivex.Single

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers

class PhotoViewModel @ViewModelInject constructor(
    private val repository: PhotoRepository
) : ViewModel(),Pageable {

    override val pageSize: Int = 30
    override var hasMore: Boolean = true
    override var currentPage: Int = 1
    override var isDownloading: Boolean = false
    val compositeDisposable = CompositeDisposable()

    private val mutableGalleryPhotoList = MutableLiveData<MutableList<PicsumPhoto>>()
    val galleryPhotoList : LiveData<MutableList<PicsumPhoto>>
        get() = mutableGalleryPhotoList

    private val mutableLikedPhotoList = MutableLiveData<MutableList<PicsumPhoto>>()
    val likedPhotoList : LiveData<MutableList<PicsumPhoto>>
        get() = mutableLikedPhotoList

    init {
        getLikedPhoto()
    }

    private fun getLikedPhoto(){
        repository.getLikesPhotoResult()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                mutableLikedPhotoList.add(it)
                getGalleryPhoto()
            }, {

            }).addTo(compositeDisposable)
    }

    fun getGalleryPhoto() {
       isDownloading = true
       repository.getGalleryPhotosResult(pageSize, currentPage++)
           .subscribeOn(Schedulers.io())
           .observeOn(AndroidSchedulers.mainThread())
           .subscribe({
               mutableGalleryPhotoList.add(isGalleryPhotoLiked(it))
               isDownloading = false
           }, {

           }).addTo(compositeDisposable)
    }


    @SuppressLint("CheckResult")
    fun changeLikePhoto(position: Int, photo: PicsumPhoto, bitmap: Bitmap){
        if (photo.isLikedPhoto) {
            Single.fromCallable {
                unlikePhoto(photo)
            }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    mutableLikedPhotoList.delete(listOf(photo))
                    findLikedPhoto(photo)
                }, {

                })
            }else {
                photo.isLikedPhoto = true
                photo.path = photo.id + ".jpg"
            Single.fromCallable {
                likePhoto(bitmap,photo)
            }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    mutableLikedPhotoList.add( listOf(photo))
                }, {

                })
        }
    }

    private fun isGalleryPhotoLiked(listPhoto:List<PicsumPhoto>):List<PicsumPhoto>{
        for (item:PicsumPhoto in listPhoto){
            for (liked:PicsumPhoto in mutableLikedPhotoList.value?: listOf()){
                if (item.id==liked.id){
                    item.isLikedPhoto = true
                }
            }
        }
        return listPhoto
    }

    private fun findLikedPhoto(photo: PicsumPhoto){
        for (item:PicsumPhoto in mutableGalleryPhotoList.value?: listOf()) {
            if (item.id == photo.id){
                item.isLikedPhoto = false
            }
        }
        mutableGalleryPhotoList.add(listOf())
    }

    private fun likePhoto(bitmap: Bitmap,photo: PicsumPhoto){
        repository.saveImage(bitmap, photo)
        repository.insertLikedPhoto(photo)
    }

    private fun unlikePhoto(photo: PicsumPhoto){
        repository.deleteImage(photo.path)
        repository.deleteLikedPhoto(photo)
    }

    override fun loadNextPage() {
        getGalleryPhoto()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

    fun <T> MutableLiveData<MutableList<T>>.add(items: List<T>?) {
        items?.let {
            val updatedItems = mutableListOf<T>().apply {
                addAll(this@add.value ?: mutableListOf())
                addAll(items)
            }
            this.value = updatedItems
        }
    }

    fun <T> MutableLiveData<MutableList<T>>.delete(items: List<T>?) {
        items?.let {
            val updatedItems = mutableListOf<T>().apply {
                addAll(this@delete.value ?: mutableListOf())
                removeAll(items)
            }
            this.value = updatedItems
        }
    }

}