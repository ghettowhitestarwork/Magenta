package com.ghettowhitestar.magentatest.ui.gallery.paginator

import androidx.paging.PagingSource
import com.ghettowhitestar.magentatest.api.PicsumApi
import com.ghettowhitestar.magentatest.data.PicsumPhoto
import retrofit2.HttpException
import java.io.IOException



class GalleryPagingSource(
    private val api: PicsumApi
) : PagingSource<Int,PicsumPhoto>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PicsumPhoto> {
        val position = params.key ?: STARTING_PAGE_INDEX

        return try {
            val response = api.getListPhotos(position,params.loadSize)
            val photos = response
                LoadResult.Page(
                    data = photos,
                    prevKey = if (position == STARTING_PAGE_INDEX) null else position - 1,
                    nextKey = if (photos.isEmpty()) null else position + 1
                )
        }catch (exception: IOException){
            LoadResult.Error(exception)
        } catch (exception: HttpException){
            LoadResult.Error(exception)
        }

    }

    companion object{
        private const val STARTING_PAGE_INDEX = 1
    }
}