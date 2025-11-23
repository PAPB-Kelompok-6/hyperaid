package com.kelompok6.hyperaid.ui.screens.measure

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.foundation.background
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.kelompok6.hyperaid.R
import com.kelompok6.hyperaid.ui.navigation.Routes

// Hapus import duplikat yang tidak perlu
// import androidx.compose.foundation.background
// import androidx.compose.foundation.layout.Box
// import androidx.compose.foundation.shape.CircleShape
// import androidx.compose.ui.draw.clip

@Composable
fun MeasureInstruction(navController: NavController) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp)
                .padding(top = 32.dp, bottom = 90.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
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
            }

            Spacer(modifier = Modifier.height(24.dp))
            Text("Measure Tips", fontWeight = FontWeight.Bold, fontSize = 24.sp)
            Spacer(Modifier.height(8.dp))
            Text(
                "When the screen flashes red, your heartbeat is being detected correctly.",
                fontSize = 13.sp,
                color = Color.DarkGray,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 26.dp)
            )

            Spacer(modifier = Modifier.height(40.dp))

            Image(
                painter = painterResource(id = R.drawable.measure_hand),
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier.size(260.dp)
            )

            Spacer(modifier = Modifier.height(28.dp))

            TipsItem("1", "Place your finger gently over the camera and flash, making sure it covers both.")
            Spacer(Modifier.height(16.dp))
            TipsItem("2", "Stay as still as possible to help the app get an accurate reading of your heartbeat.")
            Spacer(Modifier.height(16.dp))
            TipsItem("3", "Watch for the screen to flash red, that's the sign it's detecting your heartbeat correctly.")
        }

        Button(
            onClick = { navController.navigate(Routes.MEASURE_PROCESS) },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(24.dp)
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(Color(0xFF2C2C2C)),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text("START", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
        }
    }
}

// ... (Fungsi HealthDisclaimerPopup, TipsItem, dan CameraPermissionPopup tidak perlu diubah)
@Composable
fun HealthDisclaimerPopup(onNext: () -> Unit, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Health Disclaimer") },
        text = { Text("This measurement is not for medical purposes. Please consult a doctor for accurate health information.") },
        confirmButton = {
            Button(onClick = onNext) {
                Text("Next")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

@Composable
fun TipsItem(number: String, text: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .size(36.dp)
                .background(Color(0xFFD85C5C), RoundedCornerShape(10.dp)),
            contentAlignment = Alignment.Center
        ) {
            Text(number, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
        }

        Spacer(modifier = Modifier.width(16.dp))

        Text(
            text = text,
            fontSize = 14.sp,
            color = Color.DarkGray,
            lineHeight = 20.sp,
            modifier = Modifier.weight(1f)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CameraPermissionPopup(
    onAllow: () -> Unit,
    onDismiss: () -> Unit
) {

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        shape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp)
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Image(
                painter = painterResource(id = R.drawable.camera),
                contentDescription = null,
                modifier = Modifier.size(120.dp),
                contentScale = ContentScale.Fit
            )

            Spacer(Modifier.height(20.dp))

            Text(
                "Camera Permission",
                fontSize = 22.sp,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(Modifier.height(10.dp))

            Text(
                "Please allow access to the camera to start measuring",
                fontSize = 14.sp,
                color = Color.Gray,
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(26.dp))

            Button(
                onClick = onAllow,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2C2C2C))
            ) {
                Text("ALLOW", color = Color.White)
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}
