package com.kelompok6.hyperaid.ui.screens.fitsync.bmi

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.clickable
import com.kelompok6.hyperaid.ui.navigation.Routes
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BMIScreen(navController: NavHostController) {
    var selectedTab by remember { mutableStateOf("BMI") }
    var height by remember { mutableStateOf(150) }
    var weight by remember { mutableStateOf(39) }
    var age by remember { mutableStateOf(39) }
    var historyTab by remember { mutableStateOf("Recent") }

    // New state to show the result bottom sheet and store last computed values
    var showResultSheet by remember { mutableStateOf(false) }
    var lastBmi by remember { mutableStateOf(0f) }
    var lastCategory by remember { mutableStateOf("") }
    var lastMessage by remember { mutableStateOf("") }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
    ) {
        item {
            TopTabSelector(
                selectedTab = selectedTab,
                onTabSelected = { tab ->
                    selectedTab = tab
                    if (tab == "NutriTrack") {
                        navController.navigate(Routes.NUTRITRACK)
                    }
                }
            )
        }

        item {
            Text(
                text = "BMI Calculator",
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
            HeightSelector(
                height = height,
                onHeightChange = { height = it }
            )
        }

        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                WeightSelector(
                    weight = weight,
                    onWeightChange = { weight = it },
                    modifier = Modifier.weight(1f)
                )
                AgeSelector(
                    age = age,
                    onAgeChange = { age = it },
                    modifier = Modifier.weight(1f)
                )
            }
        }

        item {
            Button(
                onClick = {
                    // Compute BMI and prepare sheet content
                    val hMeters = height / 100f
                    val bmi = if (hMeters > 0f) weight / (hMeters * hMeters) else 0f
                    lastBmi = bmi

                    lastCategory = when {
                        bmi <= 0f -> "N/A"
                        bmi < 18.5f -> "Underweight"
                        bmi < 25f -> "Normal"
                        bmi < 30f -> "Overweight"
                        else -> "Obese"
                    }

                    // Use bmi ranges directly to derive the message (avoid comparing lastCategory)
                    lastMessage = when {
                        bmi <= 0f -> "Enter valid height and weight to calculate BMI."
                        bmi < 18.5f -> "In 60% of cases, poor dietary habits can pose a risk of diabetes."
                        bmi < 25f -> "Great job — your BMI is within a healthy range. Keep maintaining a balanced lifestyle."
                        bmi < 30f -> "Consider reviewing your diet and activity levels to reduce health risks."
                        else -> "It's recommended to consult with a healthcare professional for personalized advice."
                    }

                    showResultSheet = true
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2C2C2C)),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = "Count",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.White
                )
            }
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
            BMIHistoryCard()
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
        }
    }

    // Bottom sheet showing computed BMI result
    if (showResultSheet) {
        ModalBottomSheet(
            onDismissRequest = { showResultSheet = false },
            shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
            tonalElevation = 8.dp
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Title
                Text(
                    text = "Your BMI today",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(12.dp))

                // BMI value formatted with one decimal and comma separator
                val bmiText = String.format(Locale.getDefault(), "%.1f", lastBmi).replace('.', ',')
                Text(
                    text = bmiText,
                    fontSize = 48.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFD85C5C)
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Message
                Text(
                    text = lastMessage,
                    fontSize = 14.sp,
                    color = Color.Gray,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Category
                Text(
                    text = "(${lastCategory})",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = if (lastCategory == "Underweight") Color(0xFFD85C5C) else Color(0xFF2C2C2C)
                )

                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    onClick = { showResultSheet = false },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2C2C2C)),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(text = "Check BMI History", color = Color.White, fontSize = 16.sp)
                }

                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
fun TopTabSelector(selectedTab: String, onTabSelected: (String) -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        // Outer pill
        val outerShape = RoundedCornerShape(28.dp)
        Box(
            modifier = Modifier
                .widthIn(min = 260.dp)
                .height(44.dp)
                .clip(outerShape)
                .background(Color.White)
                .border(1.dp, Color(0xFFECECEC), outerShape)
        ) {
            Row(modifier = Modifier.fillMaxSize()) {
                // BMI segment
                val isBMI = selectedTab == "BMI"
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .padding(4.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .background(if (isBMI) Color(0xFFD85C5C) else Color.Transparent)
                        .clickable { onTabSelected("BMI") },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "BMI",
                        color = if (isBMI) Color.White else Color.Black,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                }

                // NutriTrack segment
                val isNutri = selectedTab == "NutriTrack"
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .padding(4.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .background(if (isNutri) Color(0xFFD85C5C) else Color.Transparent)
                        .clickable { onTabSelected("NutriTrack") },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "NutriTrack",
                        color = if (isNutri) Color.White else Color.Black,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}

@Composable
fun HeightSelector(height: Int, onHeightChange: (Int) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Height (in cm)",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
            ) {
                IconButton(
                    onClick = { onHeightChange((height - 1).coerceAtLeast(0)) },
                    modifier = Modifier
                        .border(1.dp, Color.Gray, CircleShape)
                        .size(30.dp)
                        .clip(CircleShape)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                        contentDescription = "Decrease height",
                        tint = Color.Black
                    )
                }

                Text(
                    text = height.toString(),
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier = Modifier.padding(horizontal = 64.dp)
                )

                IconButton(
                    onClick = { onHeightChange(height + 1) },
                    modifier = Modifier
                        .border(1.dp, Color.Gray, CircleShape)
                        .size(30.dp)
                        .clip(CircleShape)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                        contentDescription = "Increase height",
                        tint = Color.Black
                    )
                }
            }
        }
    }
}

@Composable
fun WeightSelector(weight: Int, onWeightChange: (Int) -> Unit, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.height(140.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Weight (in Kg)",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxSize()
            ) {
                IconButton(
                    onClick = { onWeightChange(weight - 1) },
                    modifier = Modifier
                        .border(1.dp, Color.Gray, CircleShape)
                        .size(25.dp)
                        .clip(CircleShape)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                        contentDescription = "Decrease",
                        tint = Color.Black
                    )
                }

                Text(
                    text = weight.toString(),
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )

                IconButton(
                    onClick = { onWeightChange(weight + 1) },
                    modifier = Modifier
                        .border(1.dp, Color.Gray, CircleShape)
                        .size(25.dp)
                        .clip(CircleShape)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                        contentDescription = "Increase",
                        tint = Color.Black
                    )
                }
            }
        }
    }
}

@Composable
fun AgeSelector(age: Int, onAgeChange: (Int) -> Unit, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.height(140.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Age",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxSize()
            ) {
                IconButton(
                    onClick = { onAgeChange(age - 1) },
                    modifier = Modifier
                        .border(1.dp, Color.Gray, CircleShape)
                        .size(25.dp)
                        .clip(CircleShape)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                        contentDescription = "Decrease",
                        tint = Color.Black
                    )
                }

                Text(
                    text = age.toString(),
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )

                IconButton(
                    onClick = { onAgeChange(age + 1) },
                    modifier = Modifier
                        .border(1.dp, Color.Gray, CircleShape)
                        .size(25.dp)
                        .clip(CircleShape)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                        contentDescription = "Increase",
                        tint = Color.Black
                    )
                }
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
            icon = Icons.AutoMirrored.Filled.List,
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
fun BMIHistoryCard() {
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
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "Your BMI today : ",
                            fontSize = 16.sp,
                            color = Color.Black
                        )
                        Text(
                            text = "17,8",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFD85C5C)
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "In 60% of cases, poor dietary habits can pose a risk of diabetes.",
                        fontSize = 14.sp,
                        color = Color.Gray,
                        lineHeight = 20.sp
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(20.dp))
                                .background(Color(0xFFFFE5E5))
                                .padding(horizontal = 16.dp, vertical = 8.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(8.dp)
                                        .clip(CircleShape)
                                        .background(Color(0xFFD85C5C))
                                )
                                Text(
                                    text = "Underweight",
                                    fontSize = 14.sp,
                                    color = Color(0xFFD85C5C),
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
