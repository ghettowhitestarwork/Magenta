package com.ghettowhitestar.magentatest.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

import com.ghettowhitestar.magentatest.data.PicsumPhoto
import io.reactivex.Single

/**Методы для работы с базой данных*/
@Dao
interface LikedPhotoDao {

    /**Получение всех понравившихся фотографий*/
    @Query("SELECT * FROM PicsumPhoto")
  suspend  fun getAllLikedPhotos(): List<PicsumPhoto>

    /**Добавление понравившейся фотографии*/
    @Insert
  suspend  fun insertLikedPhoto(likedPhoto: PicsumPhoto)

    /**Удаление понравившейся фотографии*/
    @Delete
  suspend  fun deleteLikedPhoto(likedPhoto: PicsumPhoto)
}