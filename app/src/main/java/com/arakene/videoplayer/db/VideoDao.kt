//package com.arakene.videoplayer.db
//
//import androidx.room.Dao
//import androidx.room.Delete
//import androidx.room.Insert
//import androidx.room.OnConflictStrategy
//import androidx.room.Query
//
//@Dao
//interface VideoDao {
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    fun insert(video: Video)
//
//    @Delete
//    fun delete(video: Video)
//
//    @Query("SELECT * FROM Video")
//    fun getAll(): List<Video>
//}