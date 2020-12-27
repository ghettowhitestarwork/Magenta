package com.ghettowhitestar.magentatest.ui.like

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.ghettowhitestar.magentatest.R
import com.ghettowhitestar.magentatest.databinding.PicturesTapeLayoutBinding
import com.ghettowhitestar.magentatest.ui.adapter.GalleryPhotoAdapter
import com.ghettowhitestar.magentatest.vm.PhotoViewModel

/**Фрагмент отображающий понравившиеся фотографии*/
class LikesFragment : Fragment(R.layout.pictures_tape_layout) {

    private val viewModel: PhotoViewModel by activityViewModels()
    private lateinit var binding: PicturesTapeLayoutBinding
    private lateinit var adapter: GalleryPhotoAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = PicturesTapeLayoutBinding.bind(view)

        adapter = GalleryPhotoAdapter { photo, bitmap -> viewModel.changeLikePhoto(photo, bitmap) }

        binding.apply {
            recyclerView.adapter = adapter
            buttonRetry.setOnClickListener { }
        }

        /**
         * Слушаем изменение в списке лайкнутых фотографий
         * Обновляем список при изменении
         */
        viewModel.likedPhotoList.observe(viewLifecycleOwner, {
            it.let { items ->
                isLikeListEmpty(it.isEmpty())
                adapter.updateItems(items)
            }
        })
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