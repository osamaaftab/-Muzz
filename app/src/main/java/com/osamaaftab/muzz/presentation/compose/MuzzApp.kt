package com.osamaaftab.muzz.presentation.compose

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.osamaaftab.muzz.presentation.compose.chat.ChatScreen
import com.osamaaftab.muzz.presentation.viewmodel.ChatViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun MuzzApp(activity: Activity) {
    val navController = rememberNavController()
    MuzzNavHost(
        activity,
        navController = navController
    )
}

@Composable
fun MuzzNavHost(
    activity: Activity,
    navController: NavHostController
) {
    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            val chatViewModel: ChatViewModel = koinViewModel()
            ChatScreen(chatViewModel) {
                activity.finish()
            }
        }
    }
}