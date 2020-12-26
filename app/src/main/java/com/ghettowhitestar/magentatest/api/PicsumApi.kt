package com.ghettowhitestar.magentatest.api

import com.ghettowhitestar.magentatest.data.PicsumPhoto
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface PicsumApi {

    @GET("v2/list")
    fun getListGalleryPhotos(
    @Query("page") page: Int,
    @Query("limit") limit :Int
    ) : Single <List<PicsumPhoto>>

    companion object {
        const val BASE_URL = "https://picsum.photos/"
    }
}