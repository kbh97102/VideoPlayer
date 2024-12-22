package com.arakene.videoplayer.ui

import android.app.Activity
import android.content.Context
import android.content.pm.ActivityInfo
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.util.Log
import androidx.annotation.OptIn
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DataSource
import androidx.media3.datasource.DefaultHttpDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.MediaSource
import androidx.media3.exoplayer.source.ProgressiveMediaSource
import androidx.media3.ui.PlayerView

@OptIn(UnstableApi::class)
@Composable
fun Player(
    link: String,
) {

    val context = LocalContext.current

    DisposableEffect(Unit) {
        val activity = context.findActivity() ?: return@DisposableEffect onDispose {  }
        val original = activity.requestedOrientation
        activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        onDispose {
            activity.requestedOrientation = original
        }
    }

    val player = remember {
        ExoPlayer.Builder(context).build().apply {
            playWhenReady = true
        }
    }

    DisposableEffect(link) {
        val dataSourceFactory: DataSource.Factory = DefaultHttpDataSource.Factory()
        val mediaSource: MediaSource =
            ProgressiveMediaSource.Factory(dataSourceFactory)
                .createMediaSource(MediaItem.fromUri(link))
        player.setMediaSource(mediaSource)
        player.prepare()

//        getMetaInfo(context, videoUri = link)

        onDispose {
            player.stop()
        }
    }

    AndroidView(modifier = Modifier.fillMaxSize(), factory = {
        PlayerView(context).apply {
            setPlayer(player)
        }
    }, update = {

    })
}

@OptIn(UnstableApi::class)
@Composable
fun Player(
    uri: Uri
) {

    val context = LocalContext.current

    DisposableEffect(Unit) {
        val activity = context.findActivity() ?: return@DisposableEffect onDispose {  }
        val original = activity.requestedOrientation
        activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        onDispose {
            activity.requestedOrientation = original
        }
    }

    val player = remember {
        ExoPlayer.Builder(context).build().apply {
            playWhenReady = true
        }
    }

    DisposableEffect(uri) {
//        val dataSourceFactory: DataSource.Factory = DefaultHttpDataSource.Factory()
//        val mediaSource: MediaSource =
//            ProgressiveMediaSource.Factory(dataSourceFactory)
//                .createMediaSource(MediaItem.fromUri(link))

        player.setMediaItem(MediaItem.fromUri(uri))
        player.prepare()

//        getMetaInfo(context, videoUri = link)

        onDispose {
            player.stop()
        }
    }

    AndroidView(modifier = Modifier.fillMaxSize(), factory = {
        PlayerView(context).apply {
            setPlayer(player)
        }
    }, update = {

    })
}


fun Context.findActivity(): Activity? = when (this) {
    is Activity -> this
    else -> null
}

fun getMetaInfo(context: Context, videoUri: String) {
    val retriever = MediaMetadataRetriever()
    retriever.setDataSource(videoUri, HashMap<String, String>())
//    retriever.setDataSource(context, videoUri)
    val rotation = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_ROTATION)
    retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_FRAME_COUNT).also {
        Log.d(">>>>", "frame? $it")
    }
    retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_HAS_IMAGE).also {
        Log.d(">>>>", "hasImage $it")
    }
    retriever.release()
    Log.d(">>>>", "rotation $rotation")
//    return rotation?.toIntOrNull() ?: 0
}