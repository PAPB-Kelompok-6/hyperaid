package com.kelompok6.hyperaid.ui.screens

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.kelompok6.hyperaid.R
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    onFinished: () -> Unit,
    delayMillis: Long = 2500L, // initial Lottie duration
    logoDelayMillis: Long = 1500L // time to show logo before overlay
) {
    var showLogo by remember { mutableStateOf(false) }
    var showRes by remember { mutableStateOf(false) }

    // define animation duration constant so we can sync navigation
    val overlayAnimationDuration = 1000 // ms, must match slideInVertically duration

    // Jalankan efek sekali: delay lalu panggil onFinished()
    LaunchedEffect(Unit) {
        try {
            Log.d("SplashScreen", "LaunchedEffect started - will delay $delayMillis ms")
            // first show Lottie animation
            delay(delayMillis)
            // then show svg logo
            showLogo = true
            Log.d("SplashScreen", "showLogo=true - will delay $logoDelayMillis ms before showing final PNG")
            delay(logoDelayMillis)
            // show final PNG splash as overlay on top of logo (keep logo visible underneath)
            showRes = true
            Log.d("SplashScreen", "showRes=true - animating final PNG then calling onFinished")
            // wait overlay animation to finish before navigating
            delay(overlayAnimationDuration.toLong() + 150L)
            Log.d("SplashScreen", "Calling onFinished() now - navigation should happen")
            onFinished()
        } catch (t: Throwable) {
            Log.e("SplashScreen", "Exception in splash LaunchedEffect", t)
            // rethrow if you want crash to surface
            throw t
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
//            .padding(24.dp),
        ,contentAlignment = Alignment.Center
    ) {
        // Show Lottie while neither logo nor final PNG is active
        if (!showLogo && !showRes) {
            val composition by rememberLottieComposition(
                LottieCompositionSpec.RawRes(R.raw.heartbeat)
            )
            val progress by animateLottieCompositionAsState(
                composition, iterations = LottieConstants.IterateForever
            )
            LottieAnimation(
                composition = composition,
                progress = { progress },
                modifier = Modifier
                    .padding(0.dp)
                    .fillMaxWidth()
                    .height(220.dp)
            )
        } else if (showLogo) {
            // show svg logo (stays visible even when showRes becomes true)
            val context = LocalContext.current
            AsyncImage(
                model = ImageRequest.Builder(context)
                    .data("file:///android_asset/logo.svg")
                    .decoderFactory(SvgDecoder.Factory())
                    .build(),
                contentDescription = "App Logo",
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .height(150.dp)
            )
        }

        // final splash image that slides in from top and covers the screen
        // it is declared after the logo so it will be drawn on top (overlap)
        AnimatedVisibility(
            visible = showRes,
            enter = slideInVertically(
                // start the slide above the container
                initialOffsetY = { -it },
                animationSpec = tween(durationMillis = overlayAnimationDuration)
            ) + fadeIn(animationSpec = tween(durationMillis = 300)),
            exit = fadeOut()
        ) {
            // apply alpha so logo beneath can still be seen if the overlay image is opaque
            // adjust alpha (0f..1f) to control how much of the logo shows through
            Image(
                painter = painterResource(id = R.drawable.red_splash),
                contentDescription = "Final Splash",
                modifier = Modifier
                    .fillMaxSize()
                    .alpha(0.92f)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SplashPreview() {
    SplashScreen(onFinished = {})
}