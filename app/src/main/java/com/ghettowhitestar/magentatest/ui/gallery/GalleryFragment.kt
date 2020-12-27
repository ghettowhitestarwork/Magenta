package com.ghettowhitestar.magentatest.ui.gallery

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.ghettowhitestar.magentatest.R
import com.ghettowhitestar.magentatest.databinding.PicturesTapeLayoutBinding
import com.ghettowhitestar.magentatest.vm.PhotoViewModel
import com.ghettowhitestar.magentatest.ui.adapter.GalleryPhotoAdapter
import com.ghettowhitestar.magentatest.paginator.PaginationListener
import dagger.hilt.android.AndroidEntryPoint

/** Фрагмент отвечающий за отображение случайных фотографий */
@AndroidEntryPoint
class GalleryFragment : Fragment(R.layout.pictures_tape_layout) {

    private val viewModel: PhotoViewModel by activityViewModels()
    private lateinit var binding: PicturesTapeLayoutBinding
    private lateinit var adapter: GalleryPhotoAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = PicturesTapeLayoutBinding.bind(view)

        adapter = GalleryPhotoAdapter { photo, bitmap -> viewModel.changeLikePhoto(photo, bitmap) }

        binding.apply {
            progressBar.visibility = View.VISIBLE
            textViewError.text = getString(R.string.connectionInternet)
            recyclerView.addOnScrollListener(PaginationListener(viewModel))
            recyclerView.adapter = adapter
            buttonRetry.setOnClickListener {
                progressBar.visibility = View.VISIBLE
                binding.textViewError.visibility = View.GONE
                binding.buttonRetry.visibility = View.GONE
                viewModel.checkNetworkConnection()
            }
        }
        /**
         * Слушаем изменения в списки отображаемых фотографий
         * обновляем адаптер при изменении
         */
        viewModel.galleryPhotoList.observe(viewLifecycleOwner, {
            it.let { items ->
                adapter.updateItems(items)
                binding.progressBar.visibility = View.GONE
            }

        })
        /** Слушаем проверку на интернет при заходе в приложение */
        viewModel.isStartNetwork.observe(viewLifecycleOwner, {
            isGalleryEmpty(it)
        })

    }

    /**
     * Показывает/скрывает вью при отключенном/подключенном интернете
     *при заходе в приложение
     */
    private fun isGalleryEmpty(isNetwork: Boolean) {
        binding.apply {
            if (isNetwork) {
                progressBar.visibility = View.GONE
                textViewError.visibility = View.VISIBLE
                buttonRetry.visibility = View.VISIBLE
            } else {
                textViewError.visibility = View.GONE
                buttonRetry.visibility = View.GONE
            }
        }
    }
}