package com.ghettowhitestar.magentatest.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ghettowhitestar.magentatest.data.PicsumPhoto

@Database(entities = [PicsumPhoto::class], version = 1)
abstract class AppDatabase : RoomDatabase()  {
        abstract fun likedPhotoDao(): LikedPhotoDao
}