package com.ghettowhitestar.magentatest

import android.app.Application
import android.app.Service
import android.content.Context
import android.net.ConnectivityManager
import androidx.core.content.ContextCompat
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
open class App: Application()