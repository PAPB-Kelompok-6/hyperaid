package com.kelompok6.hyperaid.ui.screens

import android.util.Log
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
import androidx.compose.ui.platform.LocalContext
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
    delayMillis: Long = 2800L,
    logoDelayMillis: Long = 2000L
) {
    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.heartbeat)
    )
    val progress by animateLottieCompositionAsState(
        composition, iterations = LottieConstants.IterateForever
    )
    var showLogo by remember { mutableStateOf(false) }

    // Jalankan efek sekali: delay lalu panggil onFinished()
    LaunchedEffect(Unit) {
        try {
            Log.d("SplashScreen", "LaunchedEffect started - will delay $delayMillis ms")
            delay(delayMillis)
            showLogo = true
            Log.d("SplashScreen", "showLogo=true - will delay $logoDelayMillis ms before finishing")
            delay(logoDelayMillis)
            Log.d("SplashScreen", "Calling onFinished() now")
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
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        if (!showLogo) {
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
                    .fillMaxWidth()
                    .height(200.dp)
            )
        } else {
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
    }
}

@Preview(showBackground = true)
@Composable
private fun SplashPreview() {
    SplashScreen(onFinished = {})
}