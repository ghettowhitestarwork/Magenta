package com.ghettowhitestar.magentatest

import android.view.ViewGroup
import com.ghettowhitestar.magentatest.ui.MainActivity
import com.ghettowhitestar.magentatest.ui.gallery.GalleryFragment
import com.ghettowhitestar.magentatest.ui.like.LikesFragment
import org.junit.Assert
import org.junit.Test

class UiUnitTet {

    private val fragmentLike = LikesFragment()

    private val fragmentGallery = GalleryFragment()

    @Test
    fun checkLikeLayoutId() {
        Assert.assertTrue((fragmentLike.view?.parent as? ViewGroup)?.id == R.layout.pictures_tape_layout )
    }

    @Test
    fun checkGalleryLayoutId() {
        Assert.assertTrue((fragmentGallery.view?.parent as? ViewGroup)?.id == R.layout.pictures_tape_layout )
    }
}