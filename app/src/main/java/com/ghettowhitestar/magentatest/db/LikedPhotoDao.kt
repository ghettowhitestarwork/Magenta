package com.ghettowhitestar.magentatest.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.ghettowhitestar.magentatest.data.LikedPhoto

@Dao
interface LikedPhotoDao {

    @Query("SELECT * FROM likedphoto")
    fun getAllLikedPhotos(): List<LikedPhoto>

    @Insert
    fun insertLikedPhoto(likedPhoto: LikedPhoto)

    @Delete
    fun deleteLikedPhoto(likedPhoto: LikedPhoto)
}