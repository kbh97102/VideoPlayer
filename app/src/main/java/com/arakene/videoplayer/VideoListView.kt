package com.arakene.videoplayer

import android.content.Context
import android.net.Uri
import android.os.Build
import androidx.compose.runtime.Composable
import android.Manifest
import android.content.ContentResolver
import android.content.ContentUris
import android.database.Cursor
import android.graphics.Bitmap
import android.provider.MediaStore
import android.util.Log
import android.util.Size
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresPermission
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import java.util.concurrent.TimeUnit


@Composable
fun VideoListView(

){
    val context = LocalContext.current

    var bitMap: Bitmap? by remember {
        mutableStateOf(null)
    }

// Activity Result Launcher for picking a document
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument()
    ) { uri: Uri? ->
        uri?.let {
            // Callback when a file is selected
//            onFileSelected(it)
            Log.d(">>>>", "uri $it")

            getThumbnailFromContentUri(context.contentResolver, it)?.let { result ->
                bitMap = result
            }

            getVideoMetadata(context.contentResolver, it).also { map ->
                Log.d(">>>>", "Map ${map.toString()}")
            }
        }
    }



    Column {
        Button(onClick = {
            launcher.launch(arrayOf("video/*")) // Filter for video files
        }) {
            Text(text = "Select Video")
        }

        if (bitMap != null) {

            val test = bitMap!!.asImageBitmap()

            Image(test, contentDescription = null)
        }
    }

}

fun getThumbnailFromContentUri(contentResolver: ContentResolver, contentUri: Uri): Bitmap? {
    return try {
        // Set desired thumbnail size
        val size = Size(200, 200)
        contentResolver.loadThumbnail(contentUri, size, null)
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}


fun getVideoMetadata(contentResolver: ContentResolver, contentUri: Uri): Map<String, Any?> {
    val projection = arrayOf(
        MediaStore.Video.Media.TITLE,
        MediaStore.Video.Media.DURATION,
        MediaStore.Video.Media.SIZE,
        MediaStore.Video.Media.DATE_ADDED,
        MediaStore.Video.Media.MIME_TYPE
    )

    val metadata = mutableMapOf<String, Any?>()

    val cursor: Cursor? = contentResolver.query(
        contentUri,
        projection,
        null,
        null,
        null
    )

    cursor?.use {
        if (it.moveToFirst()) {
            metadata["title"] = it.getString(it.getColumnIndexOrThrow(MediaStore.Video.Media.TITLE))
            metadata["duration"] = it.getLong(it.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION))
            metadata["size"] = it.getLong(it.getColumnIndexOrThrow(MediaStore.Video.Media.SIZE))
            metadata["dateAdded"] = it.getLong(it.getColumnIndexOrThrow(MediaStore.Video.Media.DATE_ADDED))
            metadata["mimeType"] = it.getString(it.getColumnIndexOrThrow(MediaStore.Video.Media.MIME_TYPE))
        }
    }

    return metadata
}
