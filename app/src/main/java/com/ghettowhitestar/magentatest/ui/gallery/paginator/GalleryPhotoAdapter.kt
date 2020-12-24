package com.ghettowhitestar.magentatest.ui.gallery.paginator

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Environment
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.res.ResourcesCompat
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.ghettowhitestar.magentatest.R
import com.ghettowhitestar.magentatest.data.PicsumPhoto
import com.ghettowhitestar.magentatest.databinding.ItemPhotoBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream


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
                    .asBitmap()
                    .load(photo.downloadUrl)
                    .centerCrop()
                    .error(R.drawable.ic_error)
                    .into(pictureImage)
                textViewUserName.text = photo.author

                if(photo.isLikedPhoto)
                    likeButton.setImageResource(R.drawable.ic_like)
                else
                    likeButton.setImageResource(R.drawable.ic_dislike_white)

                likeButton.setOnClickListener {
                    if (photo.isLikedPhoto){
                        photo.isLikedPhoto = false
                        likeButton.setImageResource(R.drawable.ic_dislike_white)
                        deleteImage( photo.id + ".jpg")
                    }else {
                        photo.isLikedPhoto = true
                        likeButton.setImageResource(R.drawable.ic_like)
                        val bitmap =  (pictureImage.drawable as BitmapDrawable).bitmap
                        saveImage(bitmap,photo)
                    }
                }
            }
        }

        private fun deleteImage(photoName:String){
            CoroutineScope(Dispatchers.IO).launch {
                val storageDir = File(
                        Environment.getExternalStorageDirectory()
                                .toString() + "/DCIM"
                )
                val imageFile = File(storageDir, photoName)
                if (imageFile.exists()){
                        imageFile.delete()

                }
            }
        }
        private fun saveImage(image: Bitmap,photo: PicsumPhoto) {
            CoroutineScope(Dispatchers.IO).launch {
            val imageFileName =  photo.id + ".jpg"
            val storageDir = File(
                    Environment.getExternalStorageDirectory()
                            .toString() + "/DCIM"
            )
            var success = true
            if (!storageDir.exists()) {
                success = storageDir.mkdirs()
            }
            if (success) {
                val imageFile = File(storageDir, imageFileName)
                try {
                    val fOut: OutputStream = FileOutputStream(imageFile)
                    image.compress(Bitmap.CompressFormat.JPEG, 30, fOut)
                    fOut.close()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

        }
        }
    }
}