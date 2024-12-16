package com.arakene.videoplayer.db

import android.content.Context
import androidx.room.Room

// TODO: 나중에 hilt로 변경
object DBObject {
    @Volatile
    private var INSTANCE: VideoDatabase? = null

    fun getInstance(context: Context): VideoDatabase {
        return INSTANCE ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                VideoDatabase::class.java,
                "videoplayer_database" // 데이터베이스 이름
            ).build()
            INSTANCE = instance
            instance
        }
    }

}