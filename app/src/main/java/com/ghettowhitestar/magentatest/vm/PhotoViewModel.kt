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
import com.ghettowhitestar.magentatest.utils.add
import com.ghettowhitestar.magentatest.utils.delete
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers

// Общая ViewModel для Like и Gallery Fragment
class PhotoViewModel @ViewModelInject constructor(
    private val repository: PhotoRepository
) :ViewModel(),Pageable {

    override val pageSize: Int = 30
    override var hasMore: Boolean = true
    override var currentPage: Int = 1
    override var isDownloading: Boolean = false

    private val compositeDisposable = CompositeDisposable()

    // Лайв дата для проверки на интернет при запуске приложения
    private val mutableIsStartNetwork = MutableLiveData<Boolean>()
    val isStartNetwork  : LiveData<Boolean>
        get() = mutableIsStartNetwork

    // Лайв дата для списка рандомных фотографий
    private val mutableGalleryPhotoList = MutableLiveData<MutableList<PicsumPhoto>>()
    val galleryPhotoList : LiveData<MutableList<PicsumPhoto>>
        get() = mutableGalleryPhotoList

    // Лайв дата для списка лайкнутных фотографий
    private val mutableLikedPhotoList = MutableLiveData<MutableList<PicsumPhoto>>()
    val likedPhotoList : LiveData<MutableList<PicsumPhoto>>
        get() = mutableLikedPhotoList

    init {
        getLikedPhoto()
    }

    // Запрос и обработка результата лайкнутых фотографий
    private fun getLikedPhoto(){
        repository.getLikesPhotoResult()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                mutableLikedPhotoList.add(it)
                checkNetworkConnection()
            }, {}).addTo(compositeDisposable)
    }

    fun checkNetworkConnection(){
        if(repository.isNetworkAvailable()){
            mutableIsStartNetwork.value = true
        }else{
            mutableIsStartNetwork.value = false
            getGalleryPhoto()
        }
    }

    /** Запрос и обработка результата рандомных фотографий */
    private fun getGalleryPhoto() {
       isDownloading = true
       repository.getGalleryPhotosResult(pageSize, currentPage)
           .subscribeOn(Schedulers.io())
           .observeOn(AndroidSchedulers.mainThread())
           .subscribe({
               currentPage++
               mutableGalleryPhotoList.add(isGalleryPhotoLiked(it))
               isDownloading = false
           }, {
               isDownloading = false
           }).addTo(compositeDisposable)
    }

    /**
     * Обработка события при лайке/дизлайке фотографии
     * @param bitmap файл картинки, для сохранения на телефоне
     * @param photo информация о картинке для записи/удаления из бд
     */
    @SuppressLint("CheckResult")
    fun changeLikePhoto(photo: PicsumPhoto, bitmap: Bitmap){
        if (photo.isLikedPhoto) {
            Single.fromCallable {
                unlikePhoto(photo)
            }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    mutableLikedPhotoList.delete(listOf(photo))
                    findLikedPhoto(photo)
                }, {})
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
                    photo.isLikedPhoto = false
                    photo.path = ""
                    mutableLikedPhotoList.add( listOf(photo))
                })
        }
    }

    // Проверка рандомных фотографий на то лайкнуты ли они
    // @param listPhoto список фотографий полученных по запросу
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

    // Поиск лайкнутой фотографий в списке рандомных фотографий что бы снять лайк
    // @param photo фотография у которой сняли лайк на экране понравившихся
    private fun findLikedPhoto(photo: PicsumPhoto){
        for (item:PicsumPhoto in mutableGalleryPhotoList.value?: listOf()) {
            if (item.id == photo.id){
                item.isLikedPhoto = false
            }
        }
        mutableGalleryPhotoList.add(listOf())
    }

    // Обрабатываем лайк фотографии
    // @param bitmap файл картинки, для сохранения на телефоне
    // @param photo информация о картинке для записи в бд
    private fun likePhoto(bitmap: Bitmap,photo: PicsumPhoto){
        repository.saveImage(bitmap, photo)
        repository.insertLikedPhoto(photo)
    }

    // Обрабатываем дизлайк фотографий
    // @param photo фотография которой поставили дизлайк
    private fun unlikePhoto(photo: PicsumPhoto){
        repository.deleteImage(photo.path)
        repository.deleteLikedPhoto(photo)
    }

    //Реализация подгрузи данных
    override fun loadNextPage() {
        getGalleryPhoto()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}