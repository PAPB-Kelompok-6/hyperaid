package com.kelompok6.hyperaid.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import com.kelompok6.hyperaid.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.ui.Alignment
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource

@Composable
fun NotificationScreen(navController: NavController) {

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 20.dp)
            .verticalScroll(scrollState)
    ) {

        Spacer(Modifier.height(20.dp))

        // 🔙 HEADER
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back",
                tint = Color.Black,
                modifier = Modifier
                    .size(36.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(0xFFF4F4F4))
                    .padding(8.dp)
                    .clickable { navController.popBackStack() }
            )

            Spacer(Modifier.width(12.dp))

            Text(
                "Notification",
                fontSize = 22.sp,
                fontWeight = FontWeight.SemiBold
            )
        }

        Spacer(Modifier.height(30.dp))

        // TODAY SECTION
        Text(
            "Today",
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )

        Spacer(Modifier.height(16.dp))

        // Mix of notifications: some have the green dot (unread), some don't
        NotificationItem(
            text = "Quick check! How’s your heart rate today? Tap to measure and stay on top of your health.",
            time = "5h 18m ago",
            hasDot = true
        )

        NotificationItem(
            text = "Tip of the day: Keeping your stress levels in check can boost your mood!",
            time = "12h 18m ago",
            hasDot = false
        )

        NotificationItem(
            text = "Tip of the day: Keeping your stress levels in check can boost your mood!",
            time = "12h 18m ago",
            hasDot = true
        )

        Spacer(Modifier.height(24.dp))

        // THIS WEEK
        Text(
            "This Week",
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )

        Spacer(Modifier.height(16.dp))

        NotificationItem(
            text = "It’s time for a health check! Don’t forget to measure your stress.",
            time = "1 day ago",
            hasDot = false
        )

        NotificationItem(
            text = "Ready for another day of wellness? Check your heart rate and don’t be stress.",
            time = "1 day ago",
            hasDot = true
        )

        NotificationItem(
            text = "How’s your stress level today? Take a quick check and see if you're in the healthy range.",
            time = "2 day ago",
            hasDot = false
        )

        NotificationItem(
            text = "Small steps lead to big changes. Check your heart rate now and track your progress!",
            time = "3 day ago",
            hasDot = true
        )

        NotificationItem(
            text = "Don't forget to monitor your stress! A quick check today can help keep you feeling your best.",
            time = "7 day ago",
            hasDot = false
        )

        Spacer(Modifier.height(90.dp))
    }
}

@Composable
fun NotificationItem(
    text: String,
    time: String,
    hasDot: Boolean = true // new parameter: control whether the green dot is shown
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        // LOGO
        Box(
            modifier = Modifier
                .size(64.dp)
        ) {
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .clip(CircleShape)
                    .background(Color.White)
                    .border(
                        width = 1.dp,
                        color = Color(0xFFE5E5E5),
                        shape = CircleShape
                    )
            )

            // LOGO
            Image(
                painter = painterResource(id = R.drawable.logonotif),
                contentDescription = null,
                modifier = Modifier
                    .size(110.dp)
                    .align(Alignment.Center)
            )

            // TITIK HIJAU (only shown when hasDot == true)
            if (hasDot) {
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .offset(x = 2.dp, y = (-2).dp)
                        .size(14.dp)
                        .background(Color(0xFF4CAF50), CircleShape)
                        .border(2.dp, Color.White, CircleShape)
                )
            }
        }

        Spacer(Modifier.width(14.dp))

        // --- TEXT AREA ---
        Column(modifier = Modifier.weight(1f)) {

            Text(
                text = text,
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black,
                lineHeight = 20.sp
            )

            Spacer(Modifier.height(4.dp))

            Text(
                text = time,
                fontSize = 12.sp,
                color = Color.Gray
            )
        }
    }
}
