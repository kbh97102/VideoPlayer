//package com.arakene.videoplayer.db
//
import android.graphics.Bitmap
import android.net.Uri
//import androidx.room.ColumnInfo
//import androidx.room.Entity
//import androidx.room.Index
//import androidx.room.PrimaryKey
//
//@Entity(tableName = "Video", indices = [Index(value = ["uri"], unique = true)])
//data class Video(
//    @PrimaryKey(autoGenerate = true) val uid: Int = 0,
//    @ColumnInfo(name = "uri") val uri: Uri,
//    @ColumnInfo(name = "title") val title: String,
//    @ColumnInfo(name = "thumbnail") val thumbnail : Bitmap?
//)

data class Video(

 val uri: Uri,
 val title: String,
val thumbnail : Bitmap?
)
