package com.arakene.videoplayer.ui.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arakene.videoplayer.db.Video
import com.arakene.videoplayer.db.VideoDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VideoListViewModel @Inject constructor(
    private val videoDao: VideoDao
) : ViewModel() {

    fun insertVideo(video: Video) = viewModelScope.launch {
        videoDao.insert(video)
    }

    fun getVideos() = viewModelScope.launch {
        videoDao.getAll().also {
            Log.d(">>>>", "Videos ${it.toTypedArray().contentDeepToString()}")
        }
    }

}