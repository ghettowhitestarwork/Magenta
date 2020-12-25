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

@AndroidEntryPoint
class GalleryFragment : Fragment(R.layout.pictures_tape_layout) {

    private val viewModel : PhotoViewModel by activityViewModels()
    private lateinit var binding : PicturesTapeLayoutBinding
    private lateinit var adapter: GalleryPhotoAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = PicturesTapeLayoutBinding.bind(view)

        adapter = GalleryPhotoAdapter { position, photo, bitmap -> viewModel.changeLikePhoto(position,photo, bitmap) }


        binding.apply {
            recyclerView.addOnScrollListener(PaginationListener(viewModel))
            recyclerView.adapter = adapter
            buttonRetry.setOnClickListener { viewModel.getGalleryPhoto()}
        }

        viewModel.galleryPhotoList.observe(viewLifecycleOwner,{
            it.let {items->
                adapter.updateItems(items)
            }

        })

    }
}