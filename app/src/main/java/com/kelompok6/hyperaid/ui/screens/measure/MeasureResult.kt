package com.kelompok6.hyperaid.ui.screens.measure

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.foundation.clickable

@Composable
fun MeasureResult(navController: NavController) {

    val scrollState = rememberScrollState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp)
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.Start
        ) {

            Spacer(Modifier.height(30.dp))

            // HEADER
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
                        .background(Color(0xFFF4F4F4), RoundedCornerShape(12.dp))
                        .padding(8.dp)
                        .clickable { navController.popBackStack() }
                )

                Spacer(Modifier.width(12.dp))

                Text(
                    "Result",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Spacer(Modifier.height(26.dp))

            // =======================
            // HEALTH CONDITION CARD
            // =======================
            Text("Health Condition", fontSize = 20.sp, fontWeight = FontWeight.Bold)

            Spacer(Modifier.height(16.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFE15757), RoundedCornerShape(20.dp))
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {

                        // BPM BOX
                        Column(
                            modifier = Modifier
                                .width(140.dp)
                                .background(Color(0xFFF2F2F2), RoundedCornerShape(14.dp))
                                .padding(vertical = 10.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text("79", fontSize = 34.sp, fontWeight = FontWeight.Bold)
                            Text("BPM", color = Color.Black, fontSize = 16.sp)
                        }

                        // STRESS BOX
                        Column(
                            modifier = Modifier
                                .width(140.dp)
                                .background(Color(0xFFF2F2F2), RoundedCornerShape(14.dp))
                                .padding(vertical = 10.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text("45%", fontSize = 34.sp, fontWeight = FontWeight.Bold)
                            Text("Stress", color = Color.Black, fontSize = 16.sp)
                        }
                    }

                    Spacer(Modifier.height(14.dp))

                    // TIMESTAMP ROW
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.Black, RoundedCornerShape(14.dp))
                            .padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            Modifier
                                .size(10.dp)
                                .background(Color(0xFF5CE05C), CircleShape)
                        )
                        Spacer(Modifier.width(8.dp))
                        Text("2024-09-18  09:05", color = Color.White)
                    }
                }
            }

            Spacer(Modifier.height(30.dp))

            // =======================
            // HEART RATE
            // =======================
            Text("Heart Rate", fontSize = 18.sp, fontWeight = FontWeight.Bold)

            Spacer(Modifier.height(14.dp))

            // COLOR BAR
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Box(Modifier.weight(1f).height(12.dp).background(Color(0xFF9DDDEB), RoundedCornerShape(6.dp)))
                Spacer(Modifier.width(6.dp))
                Box(Modifier.weight(1f).height(12.dp).background(Color(0xFF9DE8B3), RoundedCornerShape(6.dp)))
                Spacer(Modifier.width(6.dp))
                Box(Modifier.weight(1f).height(12.dp).background(Color(0xFFF3D97A), RoundedCornerShape(6.dp)))
                Spacer(Modifier.width(6.dp))
                Box(Modifier.weight(1f).height(12.dp).background(Color(0xFFF18A74), RoundedCornerShape(6.dp)))
                Spacer(Modifier.width(6.dp))
                Box(Modifier.weight(1f).height(12.dp).background(Color(0xFFE15757), RoundedCornerShape(6.dp)))
            }

            // Arrow indicator
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Text("▲", color = Color.Black, fontSize = 20.sp)
            }

            Spacer(Modifier.height(16.dp))

            // =======================
            // HEART RATE INFO BOX
            // =======================
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFE6F6E6), RoundedCornerShape(16.dp))
                    .padding(18.dp)
            ) {
                Column {
                    Text(
                        "Your Heart Rate : 79 BPM",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = Color(0xFF00994C)
                    )

                    Spacer(Modifier.height(10.dp))

                    Text(
                        "At 79 bpm, your heart rate is still within the healthy, normal range.",
                        fontSize = 14.sp,
                        color = Color.DarkGray
                    )

                    Spacer(Modifier.height(14.dp))

                    Box(
                        modifier = Modifier
                            .background(Color(0xFFA8E6A3), RoundedCornerShape(20.dp))
                            .padding(horizontal = 18.dp, vertical = 6.dp)
                    ) {
                        Text("●  Normal", fontSize = 14.sp, color = Color.White)
                    }
                }
            }

            Spacer(Modifier.height(36.dp))

            // =======================
            // EXCLUSIVE ADVICE
            // =======================
            Text("Exclusive Advice", fontSize = 18.sp, fontWeight = FontWeight.Bold)

            Spacer(Modifier.height(16.dp))

            AdviceItem("Stay Active",
                "Engage in regular physical activities to support cardiovascular health. Aim for at least 150 minutes of moderate-intensity aerobic exercise or 75 minutes of vigorous-intensity exercise per week, along with muscle-strengthening activities.",
                Color(0xFF4CAF50)
            )

            AdviceItem("Monitor Your Heart Rate",
                "Keep track of your heart rate during exercise and daily activities to ensure it stays within the normal range.",
                Color(0xFF4CAF50)
            )

            AdviceItem("Listen to Your Body",
                "Pay attention to any changes in your heart rate or unusual symptoms during exercise or daily routines. If you experience any concerning symptoms, consult with a healthcare professional.",
                Color(0xFF4CAF50)
            )

            AdviceItem("Be Mindful of Heart Rate During Exercise",
                "When engaging in physical activities, be aware of your target heart rate zone to ensure you are exercising at an appropriate intensity.",
                Color(0xFF4CAF50)
            )

            AdviceItem("Important Note",
                "Remember, a normal heart rate is generally a positive indicator of cardiovascular health. However, it's essential to maintain a healthy lifestyle and listen to your body for any signs of potential issues. Regular medical check-ups and consultations with healthcare professionals can help ensure your heart health is well-maintained.",
                Color(0xFFE15757)
            )

            Spacer(Modifier.height(32.dp))

            // =======================
            // STRESS LEVEL
            // =======================
            Text("Stress Level", fontSize = 18.sp, fontWeight = FontWeight.Bold)

            Spacer(Modifier.height(16.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFE6F6E6), RoundedCornerShape(16.dp))
                    .padding(18.dp)
            ) {
                Column {

                    Text(
                        "Your Stress Level : 45 %",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = Color(0xFF00994C)
                    )

                    Spacer(Modifier.height(10.dp))

                    Text(
                        "At 32%, you are still within the healthy range, your stress is manageable.",
                        fontSize = 14.sp,
                        color = Color.DarkGray
                    )

                    Spacer(Modifier.height(12.dp))

                    Box(
                        modifier = Modifier
                            .background(Color(0xFFA8E6A3), RoundedCornerShape(20.dp))
                            .padding(horizontal = 18.dp, vertical = 6.dp)
                    ) {
                        Text("●  Normal", fontSize = 14.sp, color = Color.White)
                    }
                }
            }

            Spacer(Modifier.height(30.dp))

            // =======================
            // SCIENTIFIC BASIS
            // =======================
            Text("Scientific Basis", fontSize = 18.sp, fontWeight = FontWeight.Bold)

            Spacer(Modifier.height(12.dp))

            Text(
                "According to a study published in the Journal of Stress and Health (2021), stress levels between 0% to 40% are considered within the healthy range for most individuals. The research suggests that maintaining stress levels below 40% helps prevent long-term negative impacts on physical and mental health, as the body's natural stress response remains manageable. Elevated stress above 40% can increase the risk of hypertension, anxiety, and other health complications. Regular monitoring of stress levels can assist in maintaining overall well-being.",
                fontSize = 14.sp,
                color = Color.DarkGray
            )

            Spacer(Modifier.height(100.dp))
        }
    }
}

@Composable
fun AdviceItem(title: String, desc: String, bulletColor: Color) {
    Row(modifier = Modifier.fillMaxWidth()) {

        Box(
            modifier = Modifier
                .padding(top = 6.dp)
                .size(10.dp)
                .background(bulletColor, CircleShape)
        )

        Spacer(Modifier.width(12.dp))

        Column {
            Text(title, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = bulletColor)
            Spacer(Modifier.height(6.dp))
            Text(desc, fontSize = 14.sp, color = Color.DarkGray)
            Spacer(Modifier.height(22.dp))
        }
    }
}
