package com.arakene.videoplayer.hiltmodule

import android.content.Context
import androidx.room.Room
import com.arakene.videoplayer.db.VideoDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, VideoDatabase::class.java, "videoplayer_database").build()

    @Provides
    fun provideVideoDao(database: VideoDatabase) = database.videoDao()
}