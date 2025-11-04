package com.kelompok6.hyperaid.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.kelompok6.hyperaid.ui.screens.SplashScreen
import com.kelompok6.hyperaid.ui.screens.start.LoginScreen
import com.kelompok6.hyperaid.ui.screens.start.RegisterScreen
import com.kelompok6.hyperaid.ui.screens.start.HealthDisclaimerScreen
import com.kelompok6.hyperaid.ui.screens.start.OnBoardingScreen
import com.kelompok6.hyperaid.ui.screens.start.LanguageScreen

@Composable
fun RootNavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Routes.SPLASH) {
        composable(Routes.SPLASH) {
            SplashScreen(onFinished = {
                // after splash, go to onboarding (or login/home depending on session)
                navController.navigate(Routes.ONBOARDING) {
                    popUpTo(Routes.SPLASH) { inclusive = true }
                    launchSingleTop = true
                }
            })
        }

        // OnBoarding route
        composable(Routes.ONBOARDING) {
            OnBoardingScreen(navController = navController, onContinue = {})
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

        // Health Disclaimer (Onboarding flow)
        composable(Routes.HEALTH_DISCLAIMER) {
            HealthDisclaimerScreen(navController = navController, onContinue = {})
        }

        // Language selection
        composable(Routes.LANGUAGE) {
            LanguageScreen(navController = navController)
        }
    }
}