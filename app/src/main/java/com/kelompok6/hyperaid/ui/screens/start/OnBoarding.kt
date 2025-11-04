package com.kelompok6.hyperaid.ui.screens.start

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.kelompok6.hyperaid.R
import com.airbnb.lottie.compose.LottieConstants
import com.kelompok6.hyperaid.ui.navigation.Routes

@Composable
fun OnBoardingScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController? = null,
    onContinue: () -> Unit
) {
    Log.d("OnBoardingScreen", "Composed OnBoardingScreen")

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Spacer(modifier = Modifier.height(150.dp))

            // Top logo — using a drawable resource
            Image(
                painter = painterResource(id = R.drawable.hyperaid),
                contentDescription = "Hyperaid logo",
                modifier = Modifier.widthIn(max = 200.dp)
            )

            Spacer(modifier = Modifier.height(50.dp))

            val composition by rememberLottieComposition(
                LottieCompositionSpec.RawRes(R.raw.doctorprescription)
            )
            val progress by animateLottieCompositionAsState(
                composition = composition,
                iterations = LottieConstants.IterateForever, // <-- Loop infinite
                speed = 1.0f, // Optional: bisa atur kecepatan animasi
            )
            LottieAnimation(
                composition = composition,
                progress = { progress },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(225.dp)
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Welcome to Hyperaid!",
                style = MaterialTheme.typography.displaySmall.copy(fontWeight = FontWeight.Bold),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 8.dp)
            )

            Text(
                text = "Your personal health companion for tracking Blood Pressure, BMI, Nutrition, and Heart Rate in one place.",
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(top = 8.dp, start = 12.dp, end = 12.dp)
            )

            Spacer(modifier = Modifier.padding(top = 100.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 36.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom
            ) {
                FloatingActionButton(
                    onClick = {
                        if (navController != null) {
                            navController.navigate(Routes.HEALTH_DISCLAIMER) {
                                launchSingleTop = true
                            }
                        } else {
                            onContinue()
                        }
                    },
                    containerColor = Color(0xFF222222),
                    contentColor = Color.White,
                    shape = androidx.compose.foundation.shape.CircleShape,
                    modifier = Modifier.size(56.dp)
                ) {
                    Icon(
                        // use AutoMirrored variant to avoid deprecation warning
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = "Continue",
                        modifier = Modifier.size(28.dp)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun OnBoardingPreview() {
    OnBoardingScreen(onContinue = {})
}
