package com.ghettowhitestar.magentatest.ui.like

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.ghettowhitestar.magentatest.R
import com.ghettowhitestar.magentatest.databinding.PicturesTapeLayoutBinding
import com.ghettowhitestar.magentatest.paginator.GalleryPhotoAdapter
import com.ghettowhitestar.magentatest.paginator.PhotoComparator
import com.ghettowhitestar.magentatest.ui.PhotoViewModel

class LikesFragment : Fragment(R.layout.pictures_tape_layout) {

    private val viewModel : PhotoViewModel by activityViewModels()
    private lateinit var binding : PicturesTapeLayoutBinding
    private lateinit var adapter: GalleryPhotoAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = PicturesTapeLayoutBinding.bind(view)

        adapter = GalleryPhotoAdapter(PhotoComparator) { photo,bitmap -> viewModel.likePhoto(photo, bitmap) }
        /* adapter.addLoadStateListener {
             binding.apply {
                 progressBar.isVisible = it.source.refresh is LoadState.Loading
                 recyclerView.isVisible = it.source.refresh is LoadState.NotLoading
                 buttonRetry.isVisible = it.source.refresh is LoadState.Error
                 textViewError.isVisible = it.source.refresh is LoadState.Error
             }
         }*/

        binding.apply {
            recyclerView.setHasFixedSize(true)
            recyclerView.adapter = adapter/*.withLoadStateHeaderAndFooter(
                header = GalleryPhotoLoadStateAdapter{adapter.retry()},
                footer = GalleryPhotoLoadStateAdapter{adapter.retry()}
            )*/
            buttonRetry.setOnClickListener { adapter.retry() }
        }

        viewModel.likedPhotos.observe(viewLifecycleOwner){
            adapter.submitData(viewLifecycleOwner.lifecycle,it)
        }
    }
}