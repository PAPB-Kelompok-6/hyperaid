package com.kelompok6.hyperaid.ui.screens.fitsync.nutritrack

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Preview(showBackground = true)
@Composable
fun NutriTrackScreen(navController: NavController) {
    var selectedTab by remember { mutableStateOf("NutriTrack") }
    var historyTab by remember { mutableStateOf("Recent") }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
    ) {
        item {
            TopTabSelector(
                selectedTab = selectedTab,
                onTabSelected = { selectedTab = it }
            )
        }

        item {
            Text(
                text = "Nutrition Track",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
            )
        }

        item {
            NutritionOverviewCard()
        }

        item {
            Text(
                text = "Select your meal time to add notes",
                fontSize = 16.sp,
                color = Color.Black,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
            )
        }

        item {
            MealTimeSelector()
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
        }

        item {
            HistoryTabSelector(
                selectedTab = historyTab,
                onTabSelected = { historyTab = it }
            )
        }

        item {
            MealHistoryCard()
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun TopTabSelector(selectedTab: String, onTabSelected: (String) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        TopTab(
            text = "BMI",
            isSelected = selectedTab == "BMI",
            onClick = { onTabSelected("BMI") }
        )
        TopTab(
            text = "NutriTrack",
            isSelected = selectedTab == "NutriTrack",
            onClick = { onTabSelected("NutriTrack") }
        )
    }
}

@Composable
fun TopTab(text: String, isSelected: Boolean, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelected) Color(0xFFD85C5C) else Color.White
        ),
        shape = RoundedCornerShape(20.dp),
        contentPadding = PaddingValues(horizontal = 24.dp, vertical = 8.dp)
    ) {
        Text(
            text = text,
            color = if (isSelected) Color.White else Color.Black,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
fun NutritionOverviewCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            // Circular progress indicator
            Box(
                modifier = Modifier.size(140.dp),
                contentAlignment = Alignment.Center
            ) {
                CalorieCircularProgress(
                    carbs = 50f,
                    protein = 75f,
                    fat = 25f,
                    lemak = 52f
                )
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "1101",
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    Text(
                        text = "gr/day",
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                }
            }

            // Nutrition details
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                NutritionItem("Carbo", 50f, "163/190 gr", Color(0xFF5C6BC0))
                NutritionItem("Protein", 75f, "163/190 gr", Color(0xFFD85C5C))
                NutritionItem("Serat", 25f, "163/190 gr", Color(0xFFD85C5C))
                NutritionItem("Lemak", 52f, "163/190 gr", Color(0xFF5C6BC0))
            }
        }
    }
}

@Composable
fun CalorieCircularProgress(carbs: Float, protein: Float, fat: Float, lemak: Float) {
    Canvas(modifier = Modifier.size(140.dp)) {
        val strokeWidth = 18.dp.toPx()
        val radius = (size.minDimension - strokeWidth) / 2

        // Draw background circle
        drawCircle(
            color = Color(0xFFE0E0E0),
            radius = radius,
            style = Stroke(width = strokeWidth)
        )

        // Draw carbs arc (purple-ish)
        drawArc(
            color = Color(0xFF5C6BC0),
            startAngle = -90f,
            sweepAngle = carbs * 3.6f * 0.25f,
            useCenter = false,
            style = Stroke(width = strokeWidth, cap = StrokeCap.Round),
            topLeft = Offset(strokeWidth / 2, strokeWidth / 2),
            size = androidx.compose.ui.geometry.Size(size.width - strokeWidth, size.height - strokeWidth)
        )

        // Draw protein arc (red)
        drawArc(
            color = Color(0xFFD85C5C),
            startAngle = -90f + (carbs * 3.6f * 0.25f),
            sweepAngle = protein * 3.6f * 0.25f,
            useCenter = false,
            style = Stroke(width = strokeWidth, cap = StrokeCap.Round),
            topLeft = Offset(strokeWidth / 2, strokeWidth / 2),
            size = androidx.compose.ui.geometry.Size(size.width - strokeWidth, size.height - strokeWidth)
        )

        // Draw fat arc (red continued)
        drawArc(
            color = Color(0xFFD85C5C),
            startAngle = -90f + (carbs * 3.6f * 0.25f) + (protein * 3.6f * 0.25f),
            sweepAngle = fat * 3.6f * 0.25f,
            useCenter = false,
            style = Stroke(width = strokeWidth, cap = StrokeCap.Round),
            topLeft = Offset(strokeWidth / 2, strokeWidth / 2),
            size = androidx.compose.ui.geometry.Size(size.width - strokeWidth, size.height - strokeWidth)
        )

        // Draw lemak arc (purple)
        drawArc(
            color = Color(0xFF5C6BC0),
            startAngle = -90f + (carbs * 3.6f * 0.25f) + (protein * 3.6f * 0.25f) + (fat * 3.6f * 0.25f),
            sweepAngle = lemak * 3.6f * 0.25f,
            useCenter = false,
            style = Stroke(width = strokeWidth, cap = StrokeCap.Round),
            topLeft = Offset(strokeWidth / 2, strokeWidth / 2),
            size = androidx.compose.ui.geometry.Size(size.width - strokeWidth, size.height - strokeWidth)
        )
    }
}

@Composable
fun NutritionItem(label: String, percentage: Float, amount: String, color: Color) {
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
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
            Text(
                text = "${percentage.toInt()}%",
                fontSize = 12.sp,
                color = Color.Gray
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(Color(0xFFE0E0E0))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(percentage / 100f)
                    .clip(RoundedCornerShape(4.dp))
                    .background(color)
            )
        }

        Text(
            text = amount,
            fontSize = 12.sp,
            color = Color.Gray
        )
    }
}

@Composable
fun MealTimeSelector() {
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            MealTimeCard(
                icon = "🌅",
                title = "Morning",
                time = "05.00 - 10.00",
                iconColor = Color(0xFFD85C5C)
            )
        }
        item {
            MealTimeCard(
                icon = "☀️",
                title = "Afternoon",
                time = "10.00 - 15.00",
                iconColor = Color(0xFF2C2C2C)
            )
        }
        item {
            MealTimeCard(
                icon = "🌙",
                title = "Evening",
                time = "15.00 - 20.00",
                iconColor = Color(0xFFD85C5C)
            )
        }
    }
}

@Composable
fun MealTimeCard(icon: String, title: String, time: String, iconColor: Color) {
    Card(
        modifier = Modifier
            .width(140.dp)
            .height(160.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .background(Color(0xFFFFF3E0), RoundedCornerShape(25.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = icon,
                        fontSize = 24.sp
                    )
                }

                Text(
                    text = title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black
                )

                Text(
                    text = time,
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }

            IconButton(
                onClick = { },
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(iconColor)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add",
                    tint = Color.White
                )
            }
        }
    }
}

@Composable
fun HistoryTabSelector(selectedTab: String, onTabSelected: (String) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        HistoryTab(
            text = "Recent",
            icon = Icons.Default.List,
            isSelected = selectedTab == "Recent",
            onClick = { onTabSelected("Recent") },
            modifier = Modifier.weight(1f)
        )
        HistoryTab(
            text = "History",
            icon = Icons.Default.DateRange,
            isSelected = selectedTab == "History",
            onClick = { onTabSelected("History") },
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun HistoryTab(
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

@Composable
fun MealHistoryCard() {
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
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Tuesday",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = "26/09/23 14:25:58",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(2.dp, Color(0xFFD85C5C), RoundedCornerShape(12.dp))
                    .padding(16.dp)
            ) {
                Column {
                    Text(
                        text = "Baked Sweet Potato : 2",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.Black
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text(
                            text = "Karbohidrat : 10gr",
                            fontSize = 14.sp,
                            color = Color.Gray
                        )
                        Text(
                            text = "Lemak : 10gr",
                            fontSize = 14.sp,
                            color = Color.Gray
                        )
                        Text(
                            text = "Serat : 10gr",
                            fontSize = 14.sp,
                            color = Color.Gray
                        )
                        Text(
                            text = "Protein : 10gr",
                            fontSize = 14.sp,
                            color = Color.Gray
                        )
                    }
                }
            }
        }
    }
}