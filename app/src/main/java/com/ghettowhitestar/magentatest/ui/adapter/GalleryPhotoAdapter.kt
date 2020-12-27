package com.ghettowhitestar.magentatest.ui.adapter

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ghettowhitestar.magentatest.R
import com.ghettowhitestar.magentatest.data.PicsumPhoto
import com.ghettowhitestar.magentatest.databinding.ItemPhotoBinding

/**
 * Единый адаптер для экрана лайкнутух и рандомных кратинок
 * @property items список отображаемых фотографий
 * @property listenerLike лямбда на метод в viewmodel при изменении лайка
 */
class GalleryPhotoAdapter(
    private var items: List<PicsumPhoto> = mutableListOf(),
    private val listenerLike: (PicsumPhoto, Bitmap) -> Unit
) :
    RecyclerView.Adapter<GalleryPhotoAdapter.PhotoViewHolder>() {
    private var diffUtilCallback: PhotoComparator? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val binding = ItemPhotoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PhotoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        holder.bind(items[position], listenerLike)
    }

    /**
     * Обновляем элементы в списке
     * @property items новый список с фотографиями
     */
    fun updateItems(items: MutableList<PicsumPhoto>) {
        /*  diffUtilCallback = PhotoComparator(this.items,items)
          diffUtilCallback?.let {
              val diffResult = DiffUtil.calculateDiff(it)
              this.items = mutableListOf()
              this.items = items
              diffResult.dispatchUpdatesTo(this)
          }*/
        this.items = items
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = items.size

    class PhotoViewHolder(private val binding: ItemPhotoBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(photo: PicsumPhoto, listenerLike: (PicsumPhoto, Bitmap) -> Unit) {
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
                    val bitmap = (pictureImage.drawable as BitmapDrawable).bitmap
                    listenerLike(photo, bitmap)
                }
            }
        }

        /** Устанавливаем иконку лайка в зависимости лайкнут/не лайкнут */
        private fun setLikeImage(isLike: Boolean) {
            if (isLike)
                binding.likeButton.setImageResource(R.drawable.ic_like)
            else
                binding.likeButton.setImageResource(R.drawable.ic_dislike_white)
        }
    }
}