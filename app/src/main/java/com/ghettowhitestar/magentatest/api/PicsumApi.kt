package com.ghettowhitestar.magentatest.api

import com.ghettowhitestar.magentatest.data.PicsumPhoto
import com.ghettowhitestar.magentatest.paginator.NetworkResponceState
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PicsumApi {

    companion object {
        const val BASE_URL = "https://picsum.photos/"
    }

@GET("v2/list")
suspend fun getListPhotos(
    @Query("page") page: Int,
    @Query("limit") limit :Int
) : Response<List<PicsumPhoto>>


}