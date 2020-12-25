package com.ghettowhitestar.magentatest.api

import com.ghettowhitestar.magentatest.data.PicsumPhoto
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface PicsumApi {

    companion object {
        const val BASE_URL = "https://picsum.photos/"
    }

@GET("v2/list")
 fun getListPhotos(
    @Query("page") page: Int,
    @Query("limit") limit :Int
) : Single <List<PicsumPhoto>>


}