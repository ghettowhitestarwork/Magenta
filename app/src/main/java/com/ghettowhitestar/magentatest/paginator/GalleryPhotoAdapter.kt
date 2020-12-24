package com.ghettowhitestar.magentatest.paginator

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ghettowhitestar.magentatest.R
import com.ghettowhitestar.magentatest.data.PicsumPhoto
import com.ghettowhitestar.magentatest.databinding.ItemPhotoBinding


class GalleryPhotoAdapter(diffCallback: PhotoComparator, private val listenerLike:(PicsumPhoto, Bitmap) ->Unit) : PagingDataAdapter<PicsumPhoto, GalleryPhotoAdapter.PhotoViewHolder>(
    diffCallback
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val binding = ItemPhotoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PhotoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        val currentItem = getItem(position)

        if (currentItem !=null){
            holder.bind(currentItem,listenerLike)
        }

    }

    class PhotoViewHolder(private val binding: ItemPhotoBinding) :
        RecyclerView.ViewHolder(binding.root){

        fun bind(photo: PicsumPhoto, listenerLike: (PicsumPhoto, Bitmap) -> Unit){
            binding.apply {
                Glide.with(itemView)
                    .asBitmap()
                    .load(photo.downloadUrl)
                    .centerCrop()
                    .error(R.drawable.ic_error)
                    .into(pictureImage)

                textViewUserName.text = photo.author

                setLikeImage(photo.isLikedPhoto)

                likeButton.setOnClickListener {
                        setLikeImage(!photo.isLikedPhoto)
                        val bitmap =  (pictureImage.drawable as BitmapDrawable).bitmap
                        listenerLike(photo,bitmap)
                }
            }
        }

        fun setLikeImage(isLike: Boolean){
            if(isLike)
                binding.likeButton.setImageResource(R.drawable.ic_like)
            else
                binding.likeButton.setImageResource(R.drawable.ic_dislike_white)
        }
    }
}