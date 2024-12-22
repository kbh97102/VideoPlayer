package com.arakene.videoplayer.ui.viewmodels

import Video
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VideoListViewModel @Inject constructor(
//    private val videoDao: VideoDao
) : ViewModel() {

    val videoList = mutableStateListOf<Video>()

//    fun insertVideo(video: Video) = viewModelScope.launch {
//        videoDao.insert(video)
//        delay(200)
//        getVideos()
//    }
//
//    fun getVideos() = viewModelScope.launch {
//        videoDao.getAll().also {
//
//            it.forEach { video ->
//                videoList.add(video)
//            }
//
//            Log.d(">>>>", "Videos ${it.toTypedArray().contentDeepToString()}")
//        }
//    }

}