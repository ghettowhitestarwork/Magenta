package com.ghettowhitestar.magentatest.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

import com.ghettowhitestar.magentatest.data.PicsumPhoto

@Dao
interface LikedPhotoDao {

    @Query("SELECT * FROM PicsumPhoto")
     fun getAllLikedPhotos(): PagingSource<Int, PicsumPhoto>

    @Insert
    suspend fun insertLikedPhoto(likedPhoto: PicsumPhoto)

    @Delete
    suspend fun deleteLikedPhoto(likedPhoto: PicsumPhoto)
}