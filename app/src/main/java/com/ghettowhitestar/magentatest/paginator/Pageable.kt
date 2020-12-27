package com.ghettowhitestar.magentatest.paginator

/** Интерфейс, который должны реализовывать классы, способные на пагинацию */

interface Pageable {
    /** Размер страницы с данными */
    val pageSize: Int

    /** Есть ли еще данные для подгрузки */
    var hasMore: Boolean

    /** Ныняшняя страница */
    var currentPage: Int

    /** Загружаются ли данные */
    var isDownloading: Boolean

    /** Загрузить следующую страницу с данными */
    fun loadNextPage()
}