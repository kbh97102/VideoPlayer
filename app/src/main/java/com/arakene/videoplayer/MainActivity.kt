package com.arakene.videoplayer

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.arakene.videoplayer.ui.NavigationRoute
import com.arakene.videoplayer.ui.Player
import com.arakene.videoplayer.ui.VideoListView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()

            NavHost(navController = navController, startDestination = NavigationRoute.VideoList){
                composable<NavigationRoute.VideoList> {
                    VideoListView(
                        navigate = {
                            navController.navigate(it)
                        }
                    )
                }
                composable<NavigationRoute.Player> {
                    val args = it.toRoute<NavigationRoute.Player>()

                    val uri = Uri.parse(args.uri)

                    Player(uri = uri)
                }
            }
        }
    }
}
