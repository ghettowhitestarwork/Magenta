package com.ghettowhitestar.magentatest.ui.gallery

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.ghettowhitestar.magentatest.R
import com.ghettowhitestar.magentatest.databinding.PicturesTapeLayoutBinding
import com.ghettowhitestar.magentatest.ui.gallery.paginator.GalleryPhotoAdapter
import com.ghettowhitestar.magentatest.ui.gallery.paginator.PhotoComparator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GalleryFragment : Fragment(R.layout.pictures_tape_layout) {

    private val viewModel by viewModels<GalleryViewModel>()
    private lateinit var binding : PicturesTapeLayoutBinding
    private lateinit var adapter: GalleryPhotoAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = PicturesTapeLayoutBinding.bind(view)

        adapter = GalleryPhotoAdapter(PhotoComparator)

        binding.apply {
            recyclerView.setHasFixedSize(true)
            recyclerView.adapter = adapter

        }

        viewModel.photos.observe(viewLifecycleOwner){
            adapter.submitData(viewLifecycleOwner.lifecycle,it)
        }
    }
}