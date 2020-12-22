package com.ghettowhitestar.magentatest.ui.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import java.lang.IllegalStateException

class SectionsPagerAdapter(fm: FragmentActivity)
    : FragmentStateAdapter(fm) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment =
        when (position){
            0-> PlaceholderFragment()
            1-> LikesFragment()
            else -> throw IllegalStateException("Fragment $position is not correct")
        }
}