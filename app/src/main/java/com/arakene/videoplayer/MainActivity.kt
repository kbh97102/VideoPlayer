package com.arakene.videoplayer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.arakene.videoplayer.ui.VideoListView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
//            Player("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerFun.mp4")

            VideoListView()
        }
    }
}
