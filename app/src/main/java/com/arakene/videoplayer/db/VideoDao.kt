package com.arakene.videoplayer.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert

@Dao
interface VideoDao {

    @Insert
    fun install(video: Video)

    @Delete
    fun delete(video: Video)
}