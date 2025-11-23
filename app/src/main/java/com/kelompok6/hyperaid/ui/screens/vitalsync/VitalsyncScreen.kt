package com.kelompok6.hyperaid.ui.screens.vitalsync

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.kelompok6.hyperaid.ui.navigation.Routes


//@Preview(showBackground = true)
@Composable
fun VitalsyncScreen(navController: NavController) {
    var selectedTab by remember { mutableStateOf("Recent") }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
    ) {
        item {
            PulseMeasureCard()
        }

        item {
            BloodPressureReadingCard()
        }

        item {
            AddNotesButton(navController)
        }

        item {
            TabSelector(
                selectedTab = selectedTab,
                onTabSelected = { tab ->
                    selectedTab = tab
                    if (tab == "History") {
                        navController.navigate(Routes.VITALSYNC_HISTORY)
                    }
                }
            )
        }

        // CHANGE THIS -> Grab most recent measurement
        item {
            VitalsyncDetailHistoryScreen()
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun PulseMeasureCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFE8A5A5)),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "Measure your pulse using your",
                    color = Color(0xFF2C2C2C),
                    fontSize = 16.sp
                )
                Text(
                    text = "camera!",
                    color = Color(0xFF2C2C2C),
                    fontSize = 16.sp
                )
                Spacer(modifier = Modifier.height(12.dp))
                Button(
                    onClick = { },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2C2C2C)),
                    shape = RoundedCornerShape(20.dp)
                ) {
                    Text("Measure Now", color = Color.White)
                }
            }
            Icon(
                imageVector = Icons.Default.Favorite,
                contentDescription = "Heart",
                tint = Color(0xFFB94A4A),
                modifier = Modifier.size(80.dp)
            )
        }
    }
}

@Composable
fun BloodPressureReadingCard() {
    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        Text(
            text = "Blood Pressure Reading",
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.Black,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFD85C5C)),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    BPReadingItem("135", "SYS", "mmHg", Modifier.weight(1f))
                    BPReadingItem("90", "DIA", "mmHg", Modifier.weight(1f))
                    BPReadingItem("98", "Pulse", "BPM", Modifier.weight(1f))
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFF2C2C2C))
                        .padding(12.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(10.dp)
                                .clip(CircleShape)
                                .background(Color(0xFF4CAF50))
                        )
                        Text(
                            text = "Connected to Sfigmomanometer",
                            fontSize = 14.sp,
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun BPReadingItem(value: String, label: String, unit: String, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = value,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(8.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF2C2C2C), RoundedCornerShape(8.dp))
                    .padding(vertical = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = label,
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = unit,
                        color = Color.White.copy(alpha = 0.7f),
                        fontSize = 12.sp
                    )
                }
            }
        }
    }
}

@Composable
fun AddNotesButton(navController: NavController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable(onClick = { navController.navigate(Routes.VITALSYNC_ADDNOTES) }),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Add Notes",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black
            )
            Spacer(modifier = Modifier.width(8.dp))
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Add",
                tint = Color.Black
            )
        }
    }
}

@Composable
fun TabSelector(selectedTab: String, onTabSelected: (String) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        TabButton(
            text = "Recent",
            icon = Icons.Default.List,
            isSelected = selectedTab == "Recent",
            onClick = { onTabSelected("Recent") },
            modifier = Modifier.weight(1f)
        )
        TabButton(
            text = "History",
            icon = Icons.Default.DateRange,
            isSelected = selectedTab == "History",
            onClick = { onTabSelected("History") },
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun TabButton(
    text: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier.height(50.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelected) Color(0xFF2C2C2C) else Color.White
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = text,
            tint = if (isSelected) Color.White else Color.Black,
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = text,
            color = if (isSelected) Color.White else Color.Black,
            fontSize = 16.sp
        )
    }
}
//
//@Composable
//fun ReadingDetailsCard() {
//    Card(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(16.dp),
//        colors = CardDefaults.cardColors(containerColor = Color.White),
//        shape = RoundedCornerShape(16.dp)
//    ) {
//        Column(modifier = Modifier.padding(16.dp)) {
//            Row(
//                modifier = Modifier.fillMaxWidth(),
//                horizontalArrangement = Arrangement.SpaceBetween
//            ) {
//                Text(
//                    text = "Thursday",
//                    fontSize = 16.sp,
//                    fontWeight = FontWeight.Medium
//                )
//                Text(
//                    text = "12/09/23 08:35:44 AM",
//                    fontSize = 14.sp,
//                    color = Color.Gray
//                )
//            }
//        }
//    }
//}
//
//@Composable
//fun HypertensionStageCard() {
//    Card(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(horizontal = 16.dp),
//        colors = CardDefaults.cardColors(containerColor = Color.White),
//        shape = RoundedCornerShape(16.dp)
//    ) {
//        Column(modifier = Modifier.padding(16.dp)) {
//            Text(
//                text = "Hypertension Stage 1",
//                fontSize = 18.sp,
//                fontWeight = FontWeight.SemiBold,
//                modifier = Modifier.padding(bottom = 12.dp)
//            )
//
//            // Color indicator bar
//            Row(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(20.dp)
//                    .clip(RoundedCornerShape(10.dp)),
//                horizontalArrangement = Arrangement.spacedBy(0.dp)
//            ) {
//                Box(modifier = Modifier
//                    .weight(1f)
//                    .fillMaxHeight()
//                    .background(Color(0xFF4CAF50)))
//                Box(modifier = Modifier
//                    .weight(1f)
//                    .fillMaxHeight()
//                    .background(Color(0xFFFFEB3B)))
//                Box(modifier = Modifier
//                    .weight(1f)
//                    .fillMaxHeight()
//                    .background(Color(0xFFFF9800)))
//                Box(modifier = Modifier
//                    .weight(1f)
//                    .fillMaxHeight()
//                    .background(Color(0xFFF44336)))
//                Box(modifier = Modifier
//                    .weight(1f)
//                    .fillMaxHeight()
//                    .background(Color(0xFF880E4F)))
//            }
//
//            Spacer(modifier = Modifier.height(16.dp))
//
//            ReadingRow("Systolic (Sys)", "135 mmHg", "High")
//            Spacer(modifier = Modifier.height(8.dp))
//            ReadingRow("Diastolic (Dia)", "90 mmHg", "High")
//            Spacer(modifier = Modifier.height(8.dp))
//            ReadingRow("Pulse", "98 bpm", "Normal")
//        }
//    }
//}
//
//@Composable
//fun ReadingRow(label: String, value: String, status: String) {
//    Row(
//        modifier = Modifier.fillMaxWidth(),
//        horizontalArrangement = Arrangement.SpaceBetween,
//        verticalAlignment = Alignment.CenterVertically
//    ) {
//        Text(
//            text = label,
//            fontSize = 14.sp,
//            color = Color.Black
//        )
//        Row(
//            horizontalArrangement = Arrangement.spacedBy(8.dp),
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            Text(
//                text = value,
//                fontSize = 14.sp,
//                fontWeight = FontWeight.Medium
//            )
//            Box(
//                modifier = Modifier
//                    .clip(RoundedCornerShape(12.dp))
//                    .background(
//                        if (status == "Normal") Color(0xFF4CAF50).copy(alpha = 0.2f)
//                        else Color(0xFFFF9800).copy(alpha = 0.2f)
//                    )
//                    .padding(horizontal = 12.dp, vertical = 4.dp)
//            ) {
//                Text(
//                    text = status,
//                    fontSize = 12.sp,
//                    color = if (status == "Normal") Color(0xFF2E7D32) else Color(0xFFE65100),
//                    fontWeight = FontWeight.Medium
//                )
//            }
//        }
//    }
//}
//
//@Composable
//fun RecommendedActionsCard() {
//    Card(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(16.dp),
//        colors = CardDefaults.cardColors(containerColor = Color.White),
//        shape = RoundedCornerShape(16.dp)
//    ) {
//        Column(modifier = Modifier.padding(16.dp)) {
//            Text(
//                text = "Recommended Actions",
//                fontSize = 18.sp,
//                fontWeight = FontWeight.SemiBold,
//                modifier = Modifier.padding(bottom = 12.dp)
//            )
//
//            ActionItem(
//                icon = "⚠️",
//                text = "Your systolic blood pressure is high. It is recommended to maintain a healthy diet and reduce stress. If this condition persists, please consult with a...",
//                color = Color(0xFFFF9800)
//            )
//
//            Spacer(modifier = Modifier.height(12.dp))
//
//            ActionItem(
//                icon = "⚠️",
//                text = "Your diastolic blood pressure is high. It's recommended to monitor your blood pressure regularly and make lifestyle adjustments such as reducing salt intake and managing weight.",
//                color = Color(0xFFFF9800)
//            )
//
//            Spacer(modifier = Modifier.height(12.dp))
//
//            ActionItem(
//                icon = "✅",
//                text = "Your pulse rate is normal. Continue staying active, but monitor your blood pressure closely.",
//                color = Color(0xFF4CAF50)
//            )
//        }
//    }
//}
//
//@Composable
//fun ActionItem(icon: String, text: String, color: Color) {
//    Row(
//        modifier = Modifier.fillMaxWidth(),
//        horizontalArrangement = Arrangement.spacedBy(8.dp)
//    ) {
//        Text(
//            text = icon,
//            fontSize = 16.sp,
//            modifier = Modifier.padding(top = 2.dp)
//        )
//        Text(
//            text = text,
//            fontSize = 14.sp,
//            color = Color.Black.copy(alpha = 0.8f),
//            lineHeight = 20.sp
//        )
//    }
//}
//
//@Composable
//fun UserNotesCard() {
//    Card(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(horizontal = 16.dp),
//        colors = CardDefaults.cardColors(containerColor = Color(0xFFFCE4EC)),
//        shape = RoundedCornerShape(16.dp)
//    ) {
//        Column(modifier = Modifier.padding(16.dp)) {
//            Text(
//                text = "User Notes:",
//                fontSize = 16.sp,
//                fontWeight = FontWeight.SemiBold,
//                modifier = Modifier.padding(bottom = 8.dp)
//            )
//
//            Text(
//                text = "Symptoms:",
//                fontSize = 14.sp,
//                fontWeight = FontWeight.Medium,
//                modifier = Modifier.padding(bottom = 4.dp)
//            )
//            Text(
//                text = "Feeling a bit tense and slight pressure in my head.",
//                fontSize = 14.sp,
//                color = Color.Black.copy(alpha = 0.8f),
//                modifier = Modifier.padding(bottom = 12.dp)
//            )
//
//            Text(
//                text = "Activities:",
//                fontSize = 14.sp,
//                fontWeight = FontWeight.Medium,
//                modifier = Modifier.padding(bottom = 4.dp)
//            )
//            Text(
//                text = "Skipped my morning walk today, and had a salty meal for lunch, which might have affected my blood pressure.",
//                fontSize = 14.sp,
//                color = Color.Black.copy(alpha = 0.8f)
//            )
//        }
//    }
//}