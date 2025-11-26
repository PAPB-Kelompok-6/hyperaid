package com.kelompok6.hyperaid.ui.screens.fitsync.bmi

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.VerticalAlignmentLine
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import com.github.mikephil.charting.charts.LineChart
import com.kelompok6.hyperaid.data.model.BMI

//@Preview ()
@Composable
fun BMIHistoryScreen(navController: NavController) {
//fun BMIHistoryScreen() {
    var showDetails by remember { mutableStateOf(false) }

    LazyColumn (
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
    ) {
        item {
            TopBar(
                text = "BMI History",
                onClick = { navController.popBackStack() }
//                onClick = {}
            )
        }

        item {
            Spacer(Modifier.height(120.dp))
//            BMIChartHistory()
            Spacer(Modifier.height(120.dp))
        }

        item {
            BMIHistoryCards()
            BMIHistoryCards()
        }
    }


}

@Composable
fun TopBar(text: String, onClick: () -> Unit) {
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically

    ) {
        Spacer(Modifier.width(12.dp))
        IconButton(
            onClick = onClick,
            modifier = Modifier
                .size(30.dp)
                .clip(CircleShape)
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back button",
                tint = Color.Black
            )
        }

        Spacer(Modifier.width(25.dp))
        Text(
            text = text,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
        )
    }

}

//@Composable
//fun BMIChartHistory() {
//    Row (Modifier.fillMaxSize(),
//        horizontalArrangement = Arrangement.Center,
//        verticalAlignment = Alignment.CenterVertically
//    ) {
//        Text("Anggep ada chart di sini" )
//    }
//}

@Composable
fun BMILineChart(
    records: List<BMI>,
    modifier: Modifier = Modifier
        .fillMaxWidth()
        .height(220.dp)
        .padding(16.dp)
) {
    if (records.isEmpty()) return

    val maxY = (records.maxOf { it.bmi } + 5).coerceAtLeast(40f)
    val minY = (records.minOf { it.bmi } - 5).coerceAtMost(10f)

    Canvas(modifier = modifier) {
        val spacing = 60f
        val height = size.height
        val width = size.width
        val spacePerPoint = (width - spacing) / (records.size - 1)

        val points = records.mapIndexed { index, record ->
            Offset(
                spacing + index * spacePerPoint,
                height - (record.bmi - minY) / (maxY - minY) * height
            )
        }

        val path = Path().apply {
            moveTo(points.first().x, points.first().y)
            for (i in 1 until points.size) {
                lineTo(points[i].x, points[i].y)
            }
        }

        // Draw line connecting the points
        drawPath(
            path,
            brush = SolidColor(Color.Black),
            style = Stroke(width = 4f)
        )

        // Draw points with BMI category color
        points.forEachIndexed { index, point ->
            drawCircle(
                color = bmiColor(records[index].bmi),
                radius = 10f,
                center = point
            )
        }
    }
}

//@Composable
//fun BMIChartHistory() {
//    val dummyRecords = listOf(
//        BMI("01 Jan 2025", 0,0,0, 17.2f),
//        BMI("05 Jan 2025", 0,0,0, 19.5f),
//        BMI("10 Jan 2025", 0,0,0, 22.3f),
//        BMI("15 Jan 2025", 0,0,0, 27.1f),
//        BMI("20 Jan 2025", 0,0,0, 30.4f),
//        BMI("25 Jan 2025", 0,0,0, 34.7f),
//        BMI("30 Jan 2025", 0,0,0, 37.9f)
//    )
//
//    BMILineChart(records = dummyRecords)
//}

fun bmiColor(bmi: Float): Color {
    return when {
        bmi < 18.5f -> Color(0xFF4FC3F7) // Biru - Underweight
        bmi < 25f -> Color(0xFF81C784) // Hijau - Normal
        bmi < 30f -> Color(0xFFFFF176) // Kuning - Overweight
        bmi < 35f -> Color(0xFFFFB74D) // Oranye - Obesit2y 1
        else -> Color(0xFFE57373) // Merah - Obesity 2/3
    }
}


@Composable
fun BMIHistoryCard(record: BMI) {
    val status = when {
        record.bmi < 18.5f -> "Underweight"
        record.bmi < 25f -> "Normal"
        record.bmi < 30f -> "Overweight"
        record.bmi < 35f -> "Obesity I"
        else -> "Obesity II/III"
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Your BMI: ${record.bmi}", fontWeight = FontWeight.Bold)
            Text("Status: $status", color = bmiColor(record.bmi))
            Text("Date: ${record.weight}", fontSize = 12.sp, color = Color.Gray)
        }
    }
}

@Composable
fun BMIHistoryCards() {
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
