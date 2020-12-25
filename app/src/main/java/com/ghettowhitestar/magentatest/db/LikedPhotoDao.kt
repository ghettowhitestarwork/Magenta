package com.ghettowhitestar.magentatest.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

import com.ghettowhitestar.magentatest.data.PicsumPhoto
import io.reactivex.Single

@Dao
interface LikedPhotoDao {

    @Query("SELECT * FROM PicsumPhoto")
     fun getAllLikedPhotos(): Single<List<PicsumPhoto>>

    @Insert
   fun insertLikedPhoto(likedPhoto: PicsumPhoto)

    @Delete
    fun deleteLikedPhoto(likedPhoto: PicsumPhoto)
}