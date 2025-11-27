package com.kelompok6.hyperaid.ui.screens.vitalsync

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.kelompok6.hyperaid.R
import kotlinx.coroutines.delay

@Composable
fun SfigmomanometerScreen(navController: NavController) {
    // Simple placeholder screen for starting measurement
    LaunchedEffect(Unit) {
        // wait a few seconds then return to previous (Vitalsync) screen
        delay(5000L)
        // signal previous back stack entry that measurement completed and device is connected
        navController.previousBackStackEntry?.savedStateHandle?.set("sfig_connected", true)
        navController.popBackStack()
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                androidx.compose.foundation.Image(
                    painter = painterResource(id = R.drawable.blood),
                    contentDescription = "Measuring",
                    modifier = Modifier.size(200.dp)
                )
                Text(
                    text = "Measuring Blood Pressure",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF2C2C2C),
                    modifier = Modifier.padding(top = 24.dp)
                )
                Text(
                    text = "Please remain still for accurate results.",
                    fontSize = 14.sp,
                    color = Color(0xFFD85C5C),
                    modifier = Modifier.padding(top = 12.dp)
                )
                // optional manual back button (kept hidden for now)
            }
        }
    }
}
