package com.ghettowhitestar.magentatest.paginator

interface Pageable {

    val pageSize: Int

    var hasMore: Boolean

    var currentPage: Int

    var isDownloading: Boolean

    fun loadNextPage()
}