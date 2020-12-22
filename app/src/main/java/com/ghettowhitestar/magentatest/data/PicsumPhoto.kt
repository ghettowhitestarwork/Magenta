package com.ghettowhitestar.magentatest.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PicsumPhoto(
    val id: String,
    val author: String,
    val width: Int,
    val height: Int,
    val url: String,
    val download_url: String
) : Parcelable
