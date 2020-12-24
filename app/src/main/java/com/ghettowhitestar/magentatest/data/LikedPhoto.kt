package com.ghettowhitestar.magentatest.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class LikedPhoto(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val author: String,
    val path: String
)