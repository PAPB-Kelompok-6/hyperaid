package com.kelompok6.hyperaid.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost

@Composable
private fun MainNavHost(navController: NavHostController) {
    NavHost(navController, startDestination = Routes.HOME) {
    }
}