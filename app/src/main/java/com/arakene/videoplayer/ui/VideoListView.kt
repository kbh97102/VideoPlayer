package com.arakene.videoplayer.ui

import android.content.ContentResolver
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import android.util.Size
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.Popup
import androidx.hilt.navigation.compose.hiltViewModel
import com.arakene.videoplayer.db.Video
import com.arakene.videoplayer.ui.viewmodels.VideoListViewModel
import java.util.UUID


@Composable
fun VideoListView(
    navigate: (NavigationRoute) -> Unit,
    viewModel: VideoListViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    val videoList = remember {
        viewModel.videoList
    }

    LaunchedEffect(Unit) {
        viewModel.getVideos()
    }

// Activity Result Launcher for picking a document
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickMultipleVisualMedia()
    ) { list ->
        list.map { uri ->
            uri.let {
                // Callback when a file is selected
                //            onFileSelected(it)
                Log.d(">>>>", "uri $it")

                // TODO: Android11 부터는 SAF 권한이 필요하다고 gpt가 말하는데 정확히 체크해봐야함
                context.grantUriPermission(
                    context.packageName,
                    it,
                    Intent.FLAG_GRANT_READ_URI_PERMISSION
                )

                val flag = Intent.FLAG_GRANT_READ_URI_PERMISSION
                context.contentResolver.takePersistableUriPermission(uri, flag)


                viewModel.insertVideo(
                    Video(
                        uri = it,
                        title = "testTitle ${UUID.randomUUID()}",
                        thumbnail = null
                    )
                )

                val bitmap = getThumbnailFromContentUri(context.contentResolver, it)

                getVideoMetadata(context.contentResolver, it).also { map ->
                    viewModel.insertVideo(
                        video = Video(
                            uri = uri,
                            title = map?.first ?: "",
                            thumbnail = bitmap
                        )
                    )
                }
            }
        }

    }


    var displayDeletePopUp by remember {
        mutableStateOf(false)
    }



    if (displayDeletePopUp) {
        Popup(alignment = Alignment.Center, onDismissRequest = {
            displayDeletePopUp = false
        }) {

            Column(modifier = Modifier.background(Color.White)) {
                Text("Delete?")

                Button(onClick = {
                    viewModel.deleteVideo()
                    displayDeletePopUp = false
                }) {
                    Text("OK")
                }
            }

        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp)
    ) {

        Button(onClick = {
            launcher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.VideoOnly)) // Filter for video files
        }) {
            Text(text = "Select Video")
        }


        Column(modifier = Modifier.fillMaxWidth()) {
            videoList.forEach { video ->
                TestVideoListItem(video = video, modifier = Modifier
                    .padding(top = 10.dp)
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onTap = {
                                navigate(NavigationRoute.Player(video.uri.toString()))
                            },
                            onLongPress = {
                                viewModel.targetVideo = video
                                displayDeletePopUp = true
                            }
                        )
                    }
                )
            }
        }
    }


}

@Composable
private fun TestVideoListItem(
    video: Video,
    modifier: Modifier = Modifier
) {

    Row(
        modifier = modifier
            .fillMaxWidth()
            .border(2.dp, color = Color.Gray, shape = RoundedCornerShape(4.dp))
            .padding(horizontal = 10.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        if (video.thumbnail == null) {
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .background(Color.Gray)
            )
        } else {
            Image(
                modifier = Modifier.size(50.dp),
                contentDescription = null,
                bitmap = video.thumbnail.asImageBitmap()
            )
        }

        Column {
            LaunchedEffect(video) {
                Log.e(">>>>", "title ${video.title}")
            }
            Text(text = video.title)
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


fun getVideoMetadata(contentResolver: ContentResolver, contentUri: Uri): Pair<String, Long>? {
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

    return cursor?.use {
        if (it.moveToFirst()) {
//            metadata["title"] = it.getString(it.getColumnIndexOrThrow(MediaStore.Video.Media.TITLE))
//            metadata["duration"] =
//                it.getLong(it.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION))
//            metadata["size"] = it.getLong(it.getColumnIndexOrThrow(MediaStore.Video.Media.SIZE))
//            metadata["dateAdded"] =
//                it.getLong(it.getColumnIndexOrThrow(MediaStore.Video.Media.DATE_ADDED))
//            metadata["mimeType"] =
//                it.getString(it.getColumnIndexOrThrow(MediaStore.Video.Media.MIME_TYPE))
            Pair(
                it.getString(it.getColumnIndexOrThrow(MediaStore.Video.Media.TITLE)),
                it.getLong(it.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION))
            )
        } else {
            null
        }
    }

}
