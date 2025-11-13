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
import com.kelompok6.hyperaid.ui.screens.about.AboutScreen

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

        composable(Routes.ONBOARDING) {
            OnBoardingScreen(navController = navController, onContinue = {})
        }

        composable(Routes.HOME) {
            MainScaffold()
        }

        composable(Routes.ABOUT) {
            AboutScreen(navController = navController, onContinue = {})
        }


        composable(Routes.LOGIN) {
            LoginScreen(navController)
        }
        composable(Routes.REGISTER) {
            RegisterScreen(navController)
        }

        composable(Routes.HEALTH_DISCLAIMER) {
            HealthDisclaimerScreen(navController = navController, onContinue = {})
        }

        composable(Routes.LANGUAGE) {
            LanguageScreen(navController = navController, onContinue = {})
        }

    }
}