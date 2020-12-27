package com.ghettowhitestar.magentatest.data

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
// Объкт хранимый в базе данных и POJO для запросов
@Entity
@Parcelize
data class PicsumPhoto(
    @PrimaryKey(autoGenerate = false)
    var id: String,
    @ColumnInfo(name = "author")
    val author: String,
    @SerializedName("download_url")
    val downloadUrl: String,
    var isLikedPhoto : Boolean = false,
    @ColumnInfo(name = "path")
    var path: String
): Parcelable
