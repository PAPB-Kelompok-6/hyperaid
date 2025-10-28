package com.kelompok6.hyperaid.ui.navigation

import android.util.Log
import androidx.compose.material3.Text
import androidx.compose.material3.Surface
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.kelompok6.hyperaid.ui.screens.SplashScreen

@Composable
fun RootNavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Routes.SPLASH) {
        composable(Routes.SPLASH) {
            SplashScreen(onFinished = {
                // Navigate to HOME (compose MainScaffold)
                navController.navigate(Routes.HOME) {
                    popUpTo(Routes.SPLASH) { inclusive = true }
                    launchSingleTop = true
                }
            })
        }

        // Show the main scaffold when HOME route is requested at root level
        composable(Routes.HOME) {
            MainScaffold() // MainScaffold hosts the bottom bar and internal NavHost
        }
    }
}