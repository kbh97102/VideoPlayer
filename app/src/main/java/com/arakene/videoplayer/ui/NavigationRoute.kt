package com.arakene.videoplayer.ui

import android.net.Uri
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.Serializer
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

sealed interface NavigationRoute{


    @Serializable
    data object VideoList: NavigationRoute

    @Serializable
    data class Player(val uri: String): NavigationRoute

}
