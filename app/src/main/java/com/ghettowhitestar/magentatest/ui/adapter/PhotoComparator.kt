package com.ghettowhitestar.magentatest.ui.adapter

import androidx.recyclerview.widget.DiffUtil
import com.ghettowhitestar.magentatest.data.PicsumPhoto

class PhotoComparator(private val oldItem: List<PicsumPhoto>,private val newItem: List<PicsumPhoto>) : DiffUtil.Callback() {


    override fun getOldListSize(): Int = oldItem.size


    override fun getNewListSize(): Int = newItem.size


    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldItem[oldItemPosition].id == newItem[newItemPosition].id


    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldItem[oldItemPosition] == newItem[newItemPosition]
}