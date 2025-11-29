package com.kelompok6.hyperaid.ui.screens.fitsync.bmi

import android.widget.ImageButton
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PaintingStyle.Companion.Stroke
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.VerticalAlignmentLine
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import com.github.mikephil.charting.charts.LineChart
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.kelompok6.hyperaid.R
import com.kelompok6.hyperaid.data.model.BMI
import com.kelompok6.hyperaid.data.model.Reminder
import com.kelompok6.hyperaid.data.repository.BMIRepository
import com.kelompok6.hyperaid.ui.screens.reminder.ReminderList
import java.text.SimpleDateFormat
import java.util.Locale

//@Preview ()
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BMIHistoryScreen(navController: NavController) {
    val context = LocalContext.current

    val firestore = FirebaseFirestore.getInstance()
    val auth = FirebaseAuth.getInstance()
    val bmiRepository = remember {
        BMIRepository(db = firestore, auth = auth)
    }
    val factory = remember {
        BMIViewModelFactory(repository = bmiRepository)
    }
    val viewModel: BMIViewModel = viewModel(factory = factory)
    val listBMI by viewModel.bmi.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchBMI()
    }

    var currImage by remember { mutableStateOf(R.drawable.chart) }
    val chartImage by remember { mutableStateOf(R.drawable.chart) }
    val bmi_1Image by remember { mutableStateOf(R.drawable.bmi_1) }
    val bmi_2Image by remember { mutableStateOf(R.drawable.bmi_2) }


    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("BMI History") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF5F5F5))
                .padding(paddingValues)
        )   {

            item {
                Image(
                    painter = painterResource(currImage),
                    contentDescription = "Chart BMI",
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(20.dp)
                        .border(
                            1.dp,
                            color = Color.Gray,
                            RoundedCornerShape(12.dp)
                        )
                        .clickable {
                            if (currImage == chartImage) {
                                currImage = bmi_1Image
                            } else if (currImage == bmi_1Image) {
                                currImage = bmi_2Image
                            } else {
                                currImage = chartImage
                            }
                        }
                )
            }

            if (listBMI.isEmpty()) {
                item {
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
                }
            } else {
                items(listBMI) { bmi ->
                    BMIList(bmi = bmi)
                }
            }

        }
    }
}

@Composable
fun BMIList(bmi: BMI) {
    val results = BmiInfos(bmi.bmi)
    val boxBorderColor = results.color

    val statusTextColor = when (results.status) {
        "Underweight" -> Color(0xFF2C6C76) // Darker Blue
        "Normal" -> Color(0xFF2C763F)     // Darker Green
        "Overweight" -> Color(0xFF76722C)  // Darker Yellow
        else -> Color(0xFF762C2C)         // Darker Red
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
                    text = bmi.date?.harih() ?: "N/A",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = "${bmi.date?.tanggalh() ?: "N/A"} ${bmi.date?.jamh() ?: "N/A"}",
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
                            text = String.format(Locale.US, "%.1f", bmi.bmi),
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


fun Timestamp.harih(): String {
    val hari = SimpleDateFormat("EEEE", Locale("id", "ID"))
    return hari.format(this.toDate())
}

fun Timestamp.tanggalh(): String {
    val tanggal = SimpleDateFormat("dd/MM/yy", Locale.getDefault())
    return tanggal.format(this.toDate())
}

fun Timestamp.jamh(): String {
    val jam = SimpleDateFormat("HH:mm", Locale.getDefault())
    return jam.format(this.toDate())
}

// Fungsi utilitas untuk menentukan kategori BMI dan warnanya
fun BmiInfos(bmi: Float): BmiResults {
    return when {
        bmi < 18.5f -> BmiResults(
            status = "Underweight",
            color = Color(0xFFA8E8F6), // Light Blue
            description = "Dietary habits and physical activity may need review. Consult an expert."
        )
        bmi < 25f -> BmiResults(
            status = "Normal",
            color = Color(0xFFA8F6B9), // Light Green
            description = "Congratulations! Your BMI is within the healthy range. Keep up the good work."
        )
        bmi < 30f -> BmiResults(
            status = "Overweight",
            color = Color(0xFFF4F6A8), // Light Yellow
            description = "Increased risk for health problems. Focus on balanced diet and activity."
        )
        else -> BmiResults( // bmi >= 30
            status = "Obese",
            color = Color(0xFFE08686), // Light Red
            description = "High risk of chronic diseases. Immediate lifestyle changes are recommended."
        )
    }
}

data class BmiResults(
    val status: String,
    val color: Color,
    val description: String
)