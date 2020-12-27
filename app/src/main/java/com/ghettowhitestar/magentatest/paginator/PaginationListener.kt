package com.ghettowhitestar.magentatest.paginator

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * Слушатель прокрутки списка для пагинации
 * @property pageableCallback Объект, который обрабатывает пагинацию
 * */
class PaginationListener(
    private val pageableCallback: Pageable
) : RecyclerView.OnScrollListener() {

    /**
     * Произошла прокрутка списка
     * @param recyclerView RecyclerView, на котором висит слушатель
     */
    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        val layoutManager = recyclerView.layoutManager as? LinearLayoutManager
        val visibleItemCount = layoutManager?.childCount ?: 0
        val totalItemCount = layoutManager?.itemCount ?: 0
        val firstVisibleItemPosition = layoutManager?.findFirstVisibleItemPosition() ?: 0

        if (!pageableCallback.isDownloading &&
            pageableCallback.hasMore &&
            (visibleItemCount + firstVisibleItemPosition) >= totalItemCount &&
            firstVisibleItemPosition >= 0 &&
            totalItemCount >= pageableCallback.pageSize
        ) {
            pageableCallback.loadNextPage()
        }
    }
}