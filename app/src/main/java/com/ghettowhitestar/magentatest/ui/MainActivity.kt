package com.ghettowhitestar.magentatest.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ghettowhitestar.magentatest.R
import com.ghettowhitestar.magentatest.ui.viewpager.SectionsPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val adapterViewPager by lazy { SectionsPagerAdapter(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        view_pager.adapter = adapterViewPager
        val  tabLayoutMediator = TabLayoutMediator(tabs,view_pager, TabLayoutMediator.TabConfigurationStrategy{tab,position->
            when (position){
                0->{
                    tab.setIcon(R.drawable.selector_tab_gallery)
                }
                1-> {
                    tab.setIcon(R.drawable.selector_tab_heart)
                }
            }
        })
        tabLayoutMediator.attach()
    }
}