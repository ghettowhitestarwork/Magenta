package com.ghettowhitestar.magentatest.ui.gallery

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.paging.LoadState
import com.ghettowhitestar.magentatest.R
import com.ghettowhitestar.magentatest.data.PicsumPhoto
import com.ghettowhitestar.magentatest.databinding.PicturesTapeLayoutBinding
import com.ghettowhitestar.magentatest.ui.PhotoViewModel
import com.ghettowhitestar.magentatest.paginator.GalleryPhotoAdapter
import com.ghettowhitestar.magentatest.paginator.PaginationListener
import com.ghettowhitestar.magentatest.paginator.PhotoComparator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GalleryFragment : Fragment(R.layout.pictures_tape_layout) {

    private val viewModel : PhotoViewModel by activityViewModels()
    private lateinit var binding : PicturesTapeLayoutBinding
    private lateinit var adapter: GalleryPhotoAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = PicturesTapeLayoutBinding.bind(view)

        adapter = GalleryPhotoAdapter { position, photo, bitmap -> viewModel.likePhoto(position,photo, bitmap) }


        binding.apply {
            recyclerView.addOnScrollListener(PaginationListener(viewModel))
            recyclerView.adapter = adapter
            buttonRetry.setOnClickListener { }
        }

        viewModel.res.observe(viewLifecycleOwner,{
            it.data?.let {items->
                adapter.updateItems(items)
            }

        })

    }
}