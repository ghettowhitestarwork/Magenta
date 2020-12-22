package com.ghettowhitestar.magentatest.ui.gallery.paginator

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.ghettowhitestar.magentatest.R
import com.ghettowhitestar.magentatest.data.PicsumPhoto
import com.ghettowhitestar.magentatest.databinding.ItemPhotoBinding


class GalleryPhotoAdapter(diffCallback: PhotoComparator) : PagingDataAdapter<PicsumPhoto, GalleryPhotoAdapter.PhotoViewHolder>(
    diffCallback
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val binding = ItemPhotoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PhotoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        val currentItem = getItem(position)

        if (currentItem !=null){
            holder.bind(currentItem)
        }

    }

    class PhotoViewHolder(private val binding: ItemPhotoBinding) :
        RecyclerView.ViewHolder(binding.root){

        fun bind(photo: PicsumPhoto){
            binding.apply {
                Glide.with(itemView)
                    .load(photo.download_url)
                    .centerCrop()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .error(R.drawable.ic_error)
                    .into(pictureImage)

                textViewUserName.text = photo.author
            }
        }

    }
}