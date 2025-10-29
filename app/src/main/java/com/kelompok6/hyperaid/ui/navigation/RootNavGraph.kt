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
import com.kelompok6.hyperaid.ui.screens.start.LoginScreen
import com.kelompok6.hyperaid.ui.screens.start.RegisterScreen

@Composable
fun RootNavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Routes.SPLASH) {
        composable(Routes.SPLASH) {
            SplashScreen(onFinished = {
                // if user session is not valid, navigate to login page
                // else, navigate to home
                if (true) {
                    navController.navigate(Routes.LOGIN) {
                        popUpTo(Routes.SPLASH) { inclusive = true }
                        launchSingleTop = true
                    }
                } else {
                    navController.navigate(Routes.HOME) {
                        popUpTo(Routes.SPLASH) { inclusive = true }
                        launchSingleTop = true
                    }
                }
            })
        }

        // Show the main scaffold when HOME route is requested at root level
        composable(Routes.HOME) {
            MainScaffold() // MainScaffold hosts the bottom bar and internal NavHost
        }

        // ======================================
        // AUTH ROUTES
        // ======================================
        composable(Routes.LOGIN) {
            LoginScreen(navController)
        }
        composable(Routes.REGISTER) {
            RegisterScreen(navController)
        }
    }
}