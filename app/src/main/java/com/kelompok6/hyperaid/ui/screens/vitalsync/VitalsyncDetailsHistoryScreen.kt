package com.kelompok6.hyperaid.ui.screens.vitalsync

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Preview(showBackground = true)
@Composable
fun VitalsyncDetailHistoryScreen() {
    Column {
        ReadingDetailsCard()
        HypertensionStageCard()
        RecommendedActionsCard()
        UserNotesCard()
    }
}


@Composable
fun ReadingDetailsCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Thursday",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = "12/09/23 08:35:44 AM",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }
        }
    }
}

@Composable
fun HypertensionStageCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Hypertension Stage 1",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            // Color indicator bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(20.dp)
                    .clip(RoundedCornerShape(10.dp)),
                horizontalArrangement = Arrangement.spacedBy(0.dp)
            ) {
                Box(modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .background(Color(0xFF4CAF50)))
                Box(modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .background(Color(0xFFFFEB3B)))
                Box(modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .background(Color(0xFFFF9800)))
                Box(modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .background(Color(0xFFF44336)))
                Box(modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .background(Color(0xFF880E4F)))
            }

            Spacer(modifier = Modifier.height(16.dp))

            ReadingRow("Systolic (Sys)", "135 mmHg", "High")
            Spacer(modifier = Modifier.height(8.dp))
            ReadingRow("Diastolic (Dia)", "90 mmHg", "High")
            Spacer(modifier = Modifier.height(8.dp))
            ReadingRow("Pulse", "98 bpm", "Normal")
        }
    }
}

@Composable
fun ReadingRow(label: String, value: String, status: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            fontSize = 14.sp,
            color = Color.Black
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = value,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            )
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .background(
                        if (status == "Normal") Color(0xFF4CAF50).copy(alpha = 0.2f)
                        else Color(0xFFFF9800).copy(alpha = 0.2f)
                    )
                    .padding(horizontal = 12.dp, vertical = 4.dp)
            ) {
                Text(
                    text = status,
                    fontSize = 12.sp,
                    color = if (status == "Normal") Color(0xFF2E7D32) else Color(0xFFE65100),
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

@Composable
fun RecommendedActionsCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Recommended Actions",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            ActionItem(
                icon = "⚠️",
                text = "Your systolic blood pressure is high. It is recommended to maintain a healthy diet and reduce stress. If this condition persists, please consult with a...",
                color = Color(0xFFFF9800)
            )

            Spacer(modifier = Modifier.height(12.dp))

            ActionItem(
                icon = "⚠️",
                text = "Your diastolic blood pressure is high. It's recommended to monitor your blood pressure regularly and make lifestyle adjustments such as reducing salt intake and managing weight.",
                color = Color(0xFFFF9800)
            )

            Spacer(modifier = Modifier.height(12.dp))

            ActionItem(
                icon = "✅",
                text = "Your pulse rate is normal. Continue staying active, but monitor your blood pressure closely.",
                color = Color(0xFF4CAF50)
            )
        }
    }
}

@Composable
fun ActionItem(icon: String, text: String, color: Color) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = icon,
            fontSize = 16.sp,
            modifier = Modifier.padding(top = 2.dp)
        )
        Text(
            text = text,
            fontSize = 14.sp,
            color = Color.Black.copy(alpha = 0.8f),
            lineHeight = 20.sp
        )
    }
}

@Composable
fun UserNotesCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFCE4EC)),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "User Notes:",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Text(
                text = "Symptoms:",
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            Text(
                text = "Feeling a bit tense and slight pressure in my head.",
                fontSize = 14.sp,
                color = Color.Black.copy(alpha = 0.8f),
                modifier = Modifier.padding(bottom = 12.dp)
            )

            Text(
                text = "Activities:",
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            Text(
                text = "Skipped my morning walk today, and had a salty meal for lunch, which might have affected my blood pressure.",
                fontSize = 14.sp,
                color = Color.Black.copy(alpha = 0.8f)
            )
        }
    }
}