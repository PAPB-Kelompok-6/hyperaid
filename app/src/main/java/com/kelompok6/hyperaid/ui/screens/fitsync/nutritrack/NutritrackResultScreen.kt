package com.kelompok6.hyperaid.ui.screens.fitsync.nutritrack

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kelompok6.hyperaid.R

@Preview(showBackground = true)
@Composable
fun NutritrackResultsScreen() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(32.dp))
        }

        item {
            Image(
                painter = painterResource(id = R.drawable.select_food),
                contentDescription = "select_food"
            )
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
        }

        item {
            DateTimeCard()
        }

        item {
            NutritionSummaryCard()
        }

        item {
            MealDetailsCard()
        }

        item {
            Button(
                onClick = { },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2C2C2C)),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = "Check My History",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.White
                )
            }
        }

        item {
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NutritrackResultsLoadingScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "NutriTrack",
            fontSize = 24.sp,
            fontWeight = FontWeight.ExtraBold,
            color = Color.Black,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Image(
            painter = painterResource(R.drawable.ai),
            contentDescription = "loading",
            modifier = Modifier
                .scale(3.5f)
                .padding(108.dp)
        )

        Text(
            text = "Hyper AI checking your nutrition",
            fontSize = 14.sp,
            fontWeight = FontWeight.ExtraBold,
            color = Color.Black,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Text(
            text = "Don't forget to log your meals so FitSync can track your daily nutrition.",
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 64.dp)
        )
    }
}

@Composable
fun DateTimeCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Tuesday",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black
            )
            Text(
                text = "26/09/23 14:25:58",
                fontSize = 14.sp,
                color = Color.Gray
            )
        }
    }
}

@Composable
fun NutritionSummaryCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            // Circular progress
            Box(
                modifier = Modifier.size(120.dp),
                contentAlignment = Alignment.Center
            ) {
                NutritionCircularProgress()
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "40",
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

            // Nutrition bars
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                NutritionBar("Carbohydrate", 50f, "10/190 gr", Color(0xFF5C6BC0))
                NutritionBar("Protein", 75f, "10/190 gr", Color(0xFFD85C5C))
                NutritionBar("Fiber", 25f, "10/190 gr", Color(0xFFE0E0E0))
                NutritionBar("Fat", 65f, "10/190 gr", Color(0xFF5C6BC0))
            }
        }
    }
}

@Composable
fun NutritionCircularProgress() {
    Canvas(modifier = Modifier.size(120.dp)) {
        val strokeWidth = 14.dp.toPx()
        val radius = (size.minDimension - strokeWidth) / 2

        // Background circle
        drawCircle(
            color = Color(0xFFE0E0E0),
            radius = radius,
            style = Stroke(width = strokeWidth)
        )

        // Purple arc
        drawArc(
            color = Color(0xFF5C6BC0),
            startAngle = -90f,
            sweepAngle = 120f,
            useCenter = false,
            style = Stroke(width = strokeWidth, cap = StrokeCap.Round),
            topLeft = androidx.compose.ui.geometry.Offset(strokeWidth / 2, strokeWidth / 2),
            size = androidx.compose.ui.geometry.Size(size.width - strokeWidth, size.height - strokeWidth)
        )

        // Red arc
        drawArc(
            color = Color(0xFFD85C5C),
            startAngle = 30f,
            sweepAngle = 90f,
            useCenter = false,
            style = Stroke(width = strokeWidth, cap = StrokeCap.Round),
            topLeft = androidx.compose.ui.geometry.Offset(strokeWidth / 2, strokeWidth / 2),
            size = androidx.compose.ui.geometry.Size(size.width - strokeWidth, size.height - strokeWidth)
        )
    }
}

@Composable
fun NutritionBar(label: String, percentage: Float, amount: String, color: Color) {
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = label,
                fontSize = 13.sp,
                color = Color.Black
            )
            Text(
                text = "${percentage.toInt()}%",
                fontSize = 11.sp,
                color = Color.Gray
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(6.dp)
                .background(Color(0xFFE0E0E0), RoundedCornerShape(3.dp))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(percentage / 100f)
                    .background(color, RoundedCornerShape(3.dp))
            )
        }

        Text(
            text = amount,
            fontSize = 11.sp,
            color = Color.Gray
        )
    }
}

@Composable
fun MealDetailsCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Box(
                modifier = Modifier
                    .width(4.dp)
                    .height(140.dp)
                    .background(Color(0xFFD85C5C))
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Baked Sweet Potato : 2",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(4.dp))

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