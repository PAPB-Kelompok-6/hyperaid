package com.kelompok6.hyperaid.ui.screens.fitsync.bmi

import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.fontResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.kelompok6.hyperaid.data.model.BMI
import com.kelompok6.hyperaid.data.model.Reminder
import com.kelompok6.hyperaid.data.repository.BMIRepository
import androidx.compose.foundation.clickable
import com.google.firebase.Timestamp
import com.kelompok6.hyperaid.ui.navigation.Routes
import java.text.SimpleDateFormat
import java.util.Date
import java.time.format.DateTimeFormatter
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BMIScreen(navController: NavHostController) {
    val context = LocalContext.current

    var selectedTab by remember { mutableStateOf("BMI") }
    var height by remember { mutableStateOf(150) }
    var weight by remember { mutableStateOf(39) }
    var age by remember { mutableStateOf(39) }
    var historyTab by remember { mutableStateOf("Recent") }

    val firestore = FirebaseFirestore.getInstance()
    val auth = FirebaseAuth.getInstance()
    val bmiRepository = remember {
        BMIRepository(db = firestore, auth = auth)
    }
    val factory = remember {
        BMIViewModelFactory(repository = bmiRepository)
    }
    val viewModel: BMIViewModel = viewModel(factory = factory)
    val latestBmiData by viewModel.latestBMI.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchLatestBMI()
    }

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
                    val currentUserId = auth.currentUser?.uid

                    if (currentUserId != null) {
                        val newBmiData = BMI(
                            userId = currentUserId,
                            date = Timestamp.now(),
                            height = height,
                            weight = weight,
                            age = age
                        )

                        viewModel.addBMI(newBmiData)

                        Toast.makeText(
                            context,
                            "Data BMI berhasil disimpan!",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        Toast.makeText(context, "Error: Pengguna belum login.", Toast.LENGTH_LONG).show()
                    }
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
                onTabSelected = { tab ->
                    selectedTab = tab
                    if (tab == "History") {
                        navController.navigate(Routes.FITSYNC_HISTORY)
                    }
                }
            )
        }

        item {
            BMIHistoryCard(latestBmiData)
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
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
fun BMIHistoryCard(latestBMI: BMI?) {
    if (latestBMI == null) {
        Card(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text(
                text = "Kamu belum pernah cek BMI",
                textAlign = TextAlign.Center,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
        }
    } else {
        val results = BmiInfo(latestBMI.bmi)
        val boxBorderColor = results.color

        val statusTextColor = when (results.status) {
            "Underweight" -> Color(0xFF2C6C76)
            "Normal" -> Color(0xFF2C763F)
            "Overweight" -> Color(0xFF76722C)
            else -> Color(0xFF762C2C)
        }

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
                        text = latestBMI.date?.hari() ?: "N/A",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        text = "${latestBMI.date?.tanggal() ?: "N/A"} ${latestBMI.date?.jam() ?: "N/A"}",
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(2.dp, statusTextColor, RoundedCornerShape(12.dp))
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
                                text = String.format(Locale.US, "%.1f", latestBMI.bmi),
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = statusTextColor
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = results.description,
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
                                    .background(results.color)
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
                                            .background(statusTextColor)
                                    )
                                    Text(
                                        // 6. Tampilkan status yang dinamis
                                        text = results.status,
                                        fontSize = 14.sp,
                                        color = statusTextColor,
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
}

fun Timestamp.hari(): String {
    val hari = SimpleDateFormat("EEEE", Locale("id", "ID"))
    return hari.format(this.toDate())
}

fun Timestamp.tanggal(): String {
    val tanggal = SimpleDateFormat("dd/MM/yy", Locale.getDefault())
    return tanggal.format(this.toDate())
}

fun Timestamp.jam(): String {
    val jam = SimpleDateFormat("HH:mm", Locale.getDefault())
    return jam.format(this.toDate())
}

fun BmiInfo(bmi: Float): BmiResult {
    return when {
        bmi < 18.5f -> BmiResult(
            status = "Underweight",
            color = Color(0xFFA8E8F6), // Light Blue
            description = "Dietary habits and physical activity may need review. Consult an expert."
        )
        bmi < 25f -> BmiResult(
            status = "Normal",
            color = Color(0xFFA8F6B9), // Light Green
            description = "Congratulations! Your BMI is within the healthy range. Keep up the good work."
        )
        bmi < 30f -> BmiResult(
            status = "Overweight",
            color = Color(0xFFF4F6A8), // Light Yellow
            description = "Increased risk for health problems. Focus on balanced diet and activity."
        )
        else -> BmiResult( // bmi >= 30
            status = "Obese",
            color = Color(0xFFE08686), // Light Red
            description = "High risk of chronic diseases. Immediate lifestyle changes are recommended."
        )
    }
}

data class BmiResult(
    val status: String,
    val color: Color,
    val description: String
)