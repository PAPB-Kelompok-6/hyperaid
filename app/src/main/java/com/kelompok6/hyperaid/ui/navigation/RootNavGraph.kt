package com.kelompok6.hyperaid.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.kelompok6.hyperaid.ui.helper.AuthHelper
import com.kelompok6.hyperaid.ui.screens.SplashScreen
import com.kelompok6.hyperaid.ui.screens.start.LoginScreen
import com.kelompok6.hyperaid.ui.screens.start.RegisterScreen
import com.kelompok6.hyperaid.ui.screens.start.HealthDisclaimerScreen
import com.kelompok6.hyperaid.ui.screens.start.OnBoardingScreen
import com.kelompok6.hyperaid.ui.screens.start.LanguageScreen
import com.kelompok6.hyperaid.ui.screens.about.AboutScreen
import com.kelompok6.hyperaid.ui.screens.home.NotificationScreen
import com.kelompok6.hyperaid.ui.screens.measure.MeasureInstruction
import com.kelompok6.hyperaid.ui.screens.measure.MeasureProcess
import com.kelompok6.hyperaid.ui.screens.measure.MeasureResult


@Composable
fun RootNavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Routes.SPLASH) {
        composable(Routes.SPLASH) {
            SplashScreen(onFinished = {
                // after splash, go to onboarding (or login/home depending on session)
                if (AuthHelper.isLoggedIn()) {
                    navController.navigate(Routes.LANGUAGE) {
                        popUpTo(Routes.SPLASH) { inclusive = true }
                        launchSingleTop = true
                    }
                } else {
                    navController.navigate(Routes.ONBOARDING) {
                        popUpTo(Routes.SPLASH) { inclusive = true }
                        launchSingleTop = true
                    }
                }
            })
        }

        composable(Routes.ONBOARDING) {
            OnBoardingScreen(navController = navController, onContinue = {})
        }

        composable(Routes.HOME) {
            MainScaffold(
                onLogout = {
                    AuthHelper.logout()
                    navController.navigate(Routes.LOGIN) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }

        composable(Routes.ABOUT) {
            AboutScreen(navController = navController)
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
            LanguageScreen(navController = navController)
        }

        composable(Routes.MEASURE_INSTRUCTION) {
            MeasureInstruction(navController)
        }

        composable(Routes.MEASURE_PROCESS) {
            MeasureProcess(navController)
        }

        composable(Routes.MEASURE_RESULT) {
            MeasureResult(navController)
        }
        composable(Routes.NOTIFICATION) {
            NotificationScreen(navController)
        }


    }
}