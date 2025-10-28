package com.kelompok6.hyperaid.ui.screens.fitsync.bmi

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController

@Suppress("unused", "UNUSED_PARAMETER")
@Composable
fun BMIScreen(navController: NavHostController) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = "FitSync (BMI)", style = MaterialTheme.typography.headlineSmall)
    }
}
