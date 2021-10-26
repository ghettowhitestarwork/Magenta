package com.ghettowhitestar.magentatest.ui.gallery

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.ghettowhitestar.magentatest.R
import com.ghettowhitestar.magentatest.databinding.FragmentLayoutBinding
import com.ghettowhitestar.magentatest.vm.PhotoViewModel
import com.ghettowhitestar.magentatest.ui.adapter.GalleryPhotoAdapter
import com.ghettowhitestar.magentatest.paginator.PaginationListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/** Фрагмент отвечающий за отображение случайных фотографий */
@AndroidEntryPoint
class GalleryFragment : Fragment(R.layout.fragment_layout) {

    private val viewModel: PhotoViewModel by activityViewModels()
    private lateinit var binding: FragmentLayoutBinding
    private lateinit var adapter: GalleryPhotoAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentLayoutBinding.bind(view)

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

        lifecycleScope.launchWhenStarted {

            viewModel.isStartNetwork.collect {
                isGalleryEmpty(it)
            }

            viewModel.galleryPhotoList.collect {
                it.let { items ->
                    adapter.updateItems(items)
                    binding.progressBar.visibility = View.GONE
                }
            }

            viewModel.galleryPhotoList.collect {
                it.let { items ->
                    adapter.updateItems(items)
                    binding.progressBar.visibility = View.GONE
                }
            }
        }
    }

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