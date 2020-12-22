package com.ghettowhitestar.magentatest.ui.gallery.paginator

import androidx.recyclerview.widget.DiffUtil
import com.ghettowhitestar.magentatest.data.PicsumPhoto

object PhotoComparator : DiffUtil.ItemCallback<PicsumPhoto>() {
    override fun areItemsTheSame(oldItem: PicsumPhoto, newItem: PicsumPhoto): Boolean =
        oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: PicsumPhoto, newItem: PicsumPhoto): Boolean =
        oldItem == newItem
}