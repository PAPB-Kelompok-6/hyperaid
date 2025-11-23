package com.kelompok6.hyperaid.ui.screens.measure

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.kelompok6.hyperaid.R
import com.kelompok6.hyperaid.ui.navigation.Routes
import kotlinx.coroutines.delay
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

@Composable
fun MeasureProcess(navController: NavController) {

    var progress by remember { mutableStateOf(0f) }
    var heartRate by remember { mutableStateOf(0) }
    var stress by remember { mutableStateOf(0) }
    var isMeasuringFinished by remember { mutableStateOf(false) }
    var showDisclaimer by remember { mutableStateOf(false) }

    // AUTO PROGRESS
    LaunchedEffect(true) {
        for (i in 0..100) {
            progress = i / 100f
            if (i >= 50) {
                heartRate = (70..85).random()
            }
            if (i == 100) {
                heartRate = 79
                stress = 45
                isMeasuringFinished = true
            }
            delay(40)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // BAGIAN ATAS
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFF9F9F9)),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // --- HEADER ---
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp) // Padding header
                        .padding(top = 30.dp, bottom = 20.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        modifier = Modifier
                            .clip(CircleShape)
                            .clickable { navController.popBackStack() }
                            .padding(4.dp)
                    )
                    Spacer(Modifier.width(16.dp))

                    Text(
                        text = "Measure",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                Divider(color = Color(0xFFEEEEEE), thickness = 1.dp)
                Spacer(Modifier.height(20.dp))

                // --- BPM INDICATOR ---
                BpmIndicator(bpm = heartRate)
                Spacer(Modifier.height(40.dp))

                // --- FINGERPRINT ICON ---
                Image(
                    painter = painterResource(id = R.drawable.fingerprint),
                    contentDescription = "Fingerprint",
                    modifier = Modifier.size(120.dp)
                )
                Spacer(Modifier.height(24.dp))
            }

            // BAGIAN BAWAH (BACKGROUND PUTIH)
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(horizontal = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Divider(color = Color(0xFFEEEEEE), thickness = 1.dp)
                Spacer(Modifier.height(20.dp))

                // --- MEASURING PROGRESS ---
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    ProgressCircle(progress = progress)
                    Spacer(Modifier.width(16.dp))
                    Column {
                        Text(
                            text = "Measuring...",
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            color = Color.Black
                        )
                        Spacer(Modifier.height(4.dp))
                        Text(
                            text = "As blood volume fluctuates, the blood vessel color changes",
                            fontSize = 14.sp,
                            color = Color.Gray,
                            lineHeight = 20.sp
                        )
                    }
                }

                Spacer(Modifier.height(24.dp))

                // --- DATA PREVIEW CARDS ---
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    DataCard(value = "$heartRate BPM", label = "Heart Rate", modifier = Modifier.weight(1f))
                    Spacer(Modifier.width(16.dp)) // Jarak antar card diperbaiki
                    DataCard(value = "$stress%", label = "Stress", modifier = Modifier.weight(1f))
                }
            }
        }

        // CHECK BUTTON
        Button(
            onClick = { showDisclaimer = true },
            enabled = isMeasuringFinished,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 24.dp)
                .height(56.dp)
                .align(Alignment.BottomCenter),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF2D2D2D),
                disabledContainerColor = Color(0xFFE0E0E0)
            ),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text("CHECK", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.White)
        }

        // HEALTH DISCLAIMER POPUP
        if (showDisclaimer) {
            HealthDisclaimerSheet(
                onNext = {
                    showDisclaimer = false
                    navController.navigate(Routes.MEASURE_RESULT)
                },
                onDismiss = { showDisclaimer = false }
            )
        }
    }
}

@Composable
fun BpmIndicator(bpm: Int) {
    Box(contentAlignment = Alignment.Center, modifier = Modifier.size(200.dp)) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawCircle(
                color = Color(0xFFF0F0F0),
                style = Stroke(width = 30.dp.toPx())
            )
            drawArc(
                color = Color(0xFFE15757),
                startAngle = -90f,
                sweepAngle = 360f,
                useCenter = false,
                style = Stroke(width = 30.dp.toPx(), cap = StrokeCap.Round)
            )
        }
        Text(
            text = "$bpm",
            fontSize = 52.sp,
            fontWeight = FontWeight.ExtraBold,
            color = Color.Black
        )
    }
}

@Composable
fun ProgressCircle(progress: Float) {
    Box(contentAlignment = Alignment.Center, modifier = Modifier.size(70.dp)) {
        CircularProgressIndicator(
            progress = { progress },
            modifier = Modifier.fillMaxSize(),
            color = Color(0xFFE15757),
            strokeWidth = 8.dp,
            trackColor = Color(0xFFF0F0F0)
        )
        Text(
            text = "${(progress * 100).toInt()}%",
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        )
    }
}

@Composable
fun DataCard(value: String, label: String, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .border(1.dp, Color(0xFFE8E8E8), RoundedCornerShape(16.dp))
            .background(Color.White)
            .padding(vertical = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = value,
                color = Color(0xFFE15757),
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = label,
                color = Color.Gray,
                fontSize = 14.sp
            )
        }
    }
}

@Composable
fun HealthDisclaimerSheet(
    onNext: () -> Unit,
    onDismiss: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.45f))
            .clickable(onClick = onDismiss),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(0.92f)
                .wrapContentHeight()
                .background(Color.White, RoundedCornerShape(28.dp))
                .padding(22.dp, vertical = 24.dp)
                .clickable(enabled = false) {},
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "Health Disclaimer",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF333333)
            )
            Spacer(Modifier.height(20.dp))
            DisclaimerCard(
                title = "Not a medical device",
                text = "This app offers general fitness and health information and is not a substitute for professional medical advice."
            )
            Spacer(Modifier.height(14.dp))
            DisclaimerCard(
                title = "For information purpose",
                text = "Consult your doctor or healthcare professional before starting any new exercise program or making dietary modifications."
            )
            Spacer(Modifier.height(14.dp))
            DisclaimerCard(
                title = "Features limit",
                text = "Please note that this app does not measure blood pressure, blood sugar, but can help you make a data diary."
            )
            Spacer(Modifier.height(24.dp))
            Button(
                onClick = onNext,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF2C2C2C)
                ),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text("NEXT", color = Color.White, fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
            }
        }
    }
}

@Composable
fun DisclaimerCard(title: String, text: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, Color(0xFFEAEAEA), RoundedCornerShape(16.dp))
            .padding(horizontal = 14.dp, vertical = 12.dp),
        verticalAlignment = Alignment.Top
    ) {
        Box(
            Modifier
                .padding(top = 5.dp)
                .size(7.dp)
                .background(Color(0xFFE15757), CircleShape)
        )
        Spacer(Modifier.width(12.dp))
        Column {
            Text(
                title,
                fontSize = 14.sp,
                color = Color(0xFFE15757),
                fontWeight = FontWeight.SemiBold
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text,
                fontSize = 13.sp,
                color = Color(0xFF666666),
                lineHeight = 19.sp
            )
        }
    }
}
