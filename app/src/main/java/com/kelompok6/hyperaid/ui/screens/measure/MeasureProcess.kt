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

            if (i == 100) {
                heartRate = 79
                stress = 45
                isMeasuringFinished = true   // ✅ hanya tandai bahwa selesai
            }

            delay(40)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            //.padding(24.dp)
    ) {

        Column(
            modifier = Modifier.padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally) {

            // Back button
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.Black,
                    modifier = Modifier
                        .size(36.dp)
                        .background(Color(0xFFF4F4F4), RoundedCornerShape(12.dp))
                        .padding(8.dp)
                        .clickable { navController.popBackStack() }
                )
                Spacer(Modifier.width(12.dp))
                Text("Measure", fontSize = 22.sp, fontWeight = FontWeight.SemiBold)
            }

            Spacer(Modifier.height(32.dp))

            // BIG PROGRESS CIRCLE
            CircularProgress(progressValue = progress, number = (progress * 100).toInt())

            Spacer(Modifier.height(20.dp))

            // FINGERPRINT ICON
            Image(
                painter = painterResource(id = R.drawable.fingerprint),
                contentDescription = null,
                modifier = Modifier.size(120.dp)
            )

            Spacer(Modifier.height(30.dp))

            // LOWER CARD
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(28.dp),
                colors = CardDefaults.cardColors(Color.White),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Column(modifier = Modifier.padding(24.dp)) {

                    Row(verticalAlignment = Alignment.CenterVertically) {

                        CircularProgressSmall(
                            progressValue = progress,
                            number = (progress * 100).toInt()
                        )

                        Spacer(Modifier.width(20.dp))

                        Column {
                            Text("Measuring...", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                            Text(
                                "As blood volume fluctuates, the blood vessel color changes",
                                fontSize = 14.sp,
                                color = Color.Gray
                            )
                        }
                    }

                    Spacer(Modifier.height(30.dp))

                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        ValueBox(title = "Heart Rate", value = "$heartRate BPM")
                        ValueBox(title = "Stress", value = "$stress%")
                    }

                    Spacer(Modifier.height(30.dp))

                    // CHECK BUTTON (disable kalau belum selesai)
                    Button(
                        onClick = { showDisclaimer = true },   // ✅ popup muncul hanya kalau ditekan
                        enabled = isMeasuringFinished,         // ⬅ tombol aktif setelah progress 100%
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (isMeasuringFinished) Color(0xFF2C2C2C) else Color(0xFFE0E0E0)
                        )
                    ) {
                        Text("CHECK", color = Color.White)
                    }
                }
            }
        }

        // HEALTH DISCLAIMER POPUP
        if (showDisclaimer) {
            HealthDisclaimerSheet(   // <-- ini yg benar
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
fun CircularProgress(progressValue: Float, number: Int) {

    Box(contentAlignment = Alignment.Center) {

        Canvas(modifier = Modifier.size(200.dp)) {

            // Background circle
            drawArc(
                color = Color(0xFFFFC6C6),
                startAngle = -90f,
                sweepAngle = 360f,
                useCenter = false,
                style = Stroke(22f, cap = StrokeCap.Round)
            )

            // Progress circle
            drawArc(
                color = Color(0xFFE15757),
                startAngle = -90f,
                sweepAngle = 360 * progressValue,
                useCenter = false,
                style = Stroke(22f, cap = StrokeCap.Round)
            )
        }

        Text(
            text = number.toString(),
            fontSize = 38.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun CircularProgressSmall(progressValue: Float, number: Int) {

    Box(contentAlignment = Alignment.Center) {

        Canvas(modifier = Modifier.size(80.dp)) {

            drawArc(
                color = Color(0xFFFFC6C6),
                startAngle = -90f,
                sweepAngle = 360f,
                useCenter = false,
                style = Stroke(16f, cap = StrokeCap.Round)
            )

            drawArc(
                color = Color(0xFFE15757),
                startAngle = -90f,
                sweepAngle = 360 * progressValue,
                useCenter = false,
                style = Stroke(16f, cap = StrokeCap.Round)
            )
        }

        Text(
            "$number%",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun ValueBox(value: String, title: String) {
    Column(
        modifier = Modifier
            .width(150.dp)
            .background(Color(0xFFF5F5F5), RoundedCornerShape(14.dp))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            value,
            fontWeight = FontWeight.Bold,
            fontSize = 22.sp,
            color = Color(0xFFE15757)
        )

        Text(
            title,
            fontSize = 14.sp,
            color = Color.Gray
        )
    }
}

// ... (Kode dari atas sampai sebelum HealthDisclaimerSheet tetap sama)

@Composable
fun HealthDisclaimerSheet(
    onNext: () -> Unit,
    onDismiss: () -> Unit
) {
    // Lapisan overlay gelap
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.45f)) // Sedikit lebih gelap agar kontras
            .clickable(onClick = onDismiss),
        contentAlignment = Alignment.Center
    ) {

        // --- POPUP CARD UTAMA ---
        Column(
            modifier = Modifier
                .fillMaxWidth(0.92f) // ✅ PERBAIKAN 1: Buat lebar popup 90% dari layar
                .wrapContentHeight()
                .background(Color.White, RoundedCornerShape(28.dp)) // Sudut lebih besar
                .padding(22.dp, vertical = 24.dp) // Padding horizontal dan vertikal
                .clickable(enabled = false) {}, // Mencegah klik menembus popup
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // TITLE
            Text(
                "Health Disclaimer",
                fontSize = 18.sp, // Ukuran font dibuat lebih kecil
                fontWeight = FontWeight.Bold,
                color = Color(0xFF333333) // Warna lebih soft
            )

            Spacer(Modifier.height(20.dp)) // Jarak lebih besar


            // ------ ITEM 1 ------
            DisclaimerCard(
                title = "Not a medical device",
                text = "This app offers general fitness and health information and is not a substitute for professional medical advice."
            )

            Spacer(Modifier.height(14.dp))


            // ------ ITEM 2 ------
            DisclaimerCard(
                title = "For information purpose",
                text = "Consult your doctor or healthcare professional before starting any new exercise program or making dietary modifications."
            )

            Spacer(Modifier.height(14.dp))


            // ------ ITEM 3 ------
            DisclaimerCard(
                title = "Features limit",
                text = "Please note that this app does not measure blood pressure, blood sugar, but can help you make a data diary."
            )

            Spacer(Modifier.height(24.dp)) // Jarak ke tombol lebih besar


            // BUTTON NEXT
            Button(
                onClick = onNext,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp), // Tombol sedikit lebih tinggi
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF2C2C2C)
                ),
                shape = RoundedCornerShape(16.dp) // Sudut tombol lebih besar
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
            .background(Color.White) // Latar belakang putih polos
            // ✅ PERBAIKAN 2: Gunakan border, bukan background abu-abu
            .border(1.dp, Color(0xFFEAEAEA), RoundedCornerShape(16.dp))
            .padding(horizontal = 14.dp, vertical = 12.dp), // Padding disesuaikan
        verticalAlignment = Alignment.Top
    ) {

        // Red Dot
        Box(
            Modifier
                .padding(top = 5.dp) // Atur posisi vertikal dot
                .size(7.dp) // Ukuran dot lebih kecil
                .background(Color(0xFFE15757), CircleShape)
        )

        Spacer(Modifier.width(12.dp))

        Column {
            Text(
                title,
                fontSize = 14.sp, // Font judul lebih kecil
                color = Color(0xFFE15757),
                fontWeight = FontWeight.SemiBold
            )
            Spacer(Modifier.height(4.dp)) // Jarak antara judul dan teks
            Text(
                text,
                fontSize = 13.sp,
                color = Color(0xFF666666), // Warna teks lebih soft
                lineHeight = 19.sp // Jarak antar baris
            )
        }
    }
}
