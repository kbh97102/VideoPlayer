package com.arakene.videoplayer.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [Video::class], version = 1)
@TypeConverters(UriConverters::class, BitmapConverter::class)
abstract class VideoDatabase : RoomDatabase() {

    abstract fun videoDao(): VideoDao

}