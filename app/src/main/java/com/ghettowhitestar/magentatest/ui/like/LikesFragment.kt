package com.ghettowhitestar.magentatest.ui.like

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.ghettowhitestar.magentatest.R
import com.ghettowhitestar.magentatest.databinding.FragmentLayoutBinding
import com.ghettowhitestar.magentatest.ui.adapter.GalleryPhotoAdapter
import com.ghettowhitestar.magentatest.vm.PhotoViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**Фрагмент отображающий понравившиеся фотографии*/
class LikesFragment : Fragment(R.layout.fragment_layout) {

    private val viewModel: PhotoViewModel by activityViewModels()
    private lateinit var binding: FragmentLayoutBinding
    private lateinit var adapter: GalleryPhotoAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentLayoutBinding.bind(view)

        adapter = GalleryPhotoAdapter { photo, bitmap -> viewModel.changeLikePhoto(photo, bitmap) }

        binding.apply {
            recyclerView.adapter = adapter
            buttonRetry.setOnClickListener { }
        }

        /**
         * Слушаем изменение в списке лайкнутых фотографий
         * Обновляем список при изменении
         */
        lifecycleScope.launch {
            viewModel.likedPhotoList
                .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .collect {
                it.let { items ->
                    isLikeListEmpty(it.isEmpty())
                    adapter.updateItems(items)
                }
            }
        }

    }

    /** Показывает сообщение, если нет лайкнутых картинок */
    private fun isLikeListEmpty(isEmpty: Boolean) {
        if (isEmpty) {
            binding.textViewError.visibility = View.VISIBLE
        } else {
            binding.textViewError.visibility = View.GONE
        }
    }
}