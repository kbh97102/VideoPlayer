package com.arakene.videoplayer

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class VideoPlayerApplication: Application() {

    override fun onCreate() {
        super.onCreate()
    }

}