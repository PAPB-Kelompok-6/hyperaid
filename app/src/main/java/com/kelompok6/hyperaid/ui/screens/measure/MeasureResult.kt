package com.kelompok6.hyperaid.ui.screens.measure

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import com.kelompok6.hyperaid.ui.navigation.Routes


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
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.Black,
                    modifier = Modifier
                        .size(36.dp)
                        .background(Color(0xFFF4F4F4), RoundedCornerShape(12.dp))
                        .padding(8.dp)
                        .clickable { navController.navigate(Routes.HOME) }
                )

                Spacer(Modifier.width(12.dp))

                Text(
                    "Result",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Spacer(Modifier.height(26.dp))

            // HEALTH CONDITION CARD
            Text("Health Condition", fontSize = 20.sp, fontWeight = FontWeight.Bold)

            Spacer(Modifier.height(16.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFE15757), RoundedCornerShape(22.dp)) // RED
            ) {

                Column(modifier = Modifier.padding(top = 28.dp, bottom = 0.dp)) {

                    // --- BOX ROW ---
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        val boxWidth = 130.dp

                        // BPM BOX
                        Column(
                            modifier = Modifier
                                .width(boxWidth)
                                .background(Color.White, RoundedCornerShape(18.dp))
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 18.dp, bottom = 10.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text("79", fontSize = 36.sp, fontWeight = FontWeight.Bold)
                            }
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(
                                        Color(0xFF2D2D2D),
                                        shape = RoundedCornerShape(
                                            topStart = 0.dp,      // Pojok kiri atas lurus
                                            topEnd = 0.dp,        // Pojok kanan atas lurus
                                            bottomStart = 18.dp,  // Pojok kiri bawah melengkung (samakan dengan induknya)
                                            bottomEnd = 18.dp     // Pojok kanan bawah melengkung (samakan dengan induknya)
                                        )
                                    )
                                    .padding(vertical = 12.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text("BPM", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                            }
                        }

                        // STRESS BOX
                        Column(
                            modifier = Modifier
                                .width(boxWidth)
                                .background(Color.White, RoundedCornerShape(18.dp))
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 18.dp, bottom = 10.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text("45%", fontSize = 36.sp, fontWeight = FontWeight.Bold)
                            }
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(
                                        Color(0xFF2D2D2D),
                                        shape = RoundedCornerShape(
                                            topStart = 0.dp,
                                            topEnd = 0.dp,
                                            bottomStart = 18.dp,
                                            bottomEnd = 18.dp
                                        )
                                    )
                                    .padding(vertical = 12.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text("Stress", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
                            }
                        }
                    }

                    Spacer(Modifier.height(22.dp))

                    // --- TIMESTAMP AREA ---
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                Color(0xFF222222),
                                RoundedCornerShape(bottomStart = 18.dp, bottomEnd = 18.dp)
                            )
                            .padding(horizontal = 20.dp, vertical = 14.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                Modifier
                                    .size(10.dp)
                                    .background(Color(0xFF5CE05C), CircleShape)
                            )
                            Spacer(Modifier.width(10.dp))
                            Text("2024-09-18   09:05", color = Color.White, fontSize = 15.sp)
                        }
                    }
                }
            }

            Spacer(Modifier.height(25.dp))

            // HEART RATE
            Text("Heart Rate", fontSize = 18.sp, fontWeight = FontWeight.Bold)

            Spacer(Modifier.height(14.dp))

            // COLOR BAR
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Box(Modifier
                    .weight(1f)
                    .height(12.dp)
                    .background(Color(0xFF9DDDEB), RoundedCornerShape(6.dp)))
                Spacer(Modifier.width(6.dp))
                Box(Modifier
                    .weight(1f)
                    .height(12.dp)
                    .background(Color(0xFF9DE8B3), RoundedCornerShape(6.dp)))
                Spacer(Modifier.width(6.dp))
                Box(Modifier
                    .weight(1f)
                    .height(12.dp)
                    .background(Color(0xFFF3D97A), RoundedCornerShape(6.dp)))
                Spacer(Modifier.width(6.dp))
                Box(Modifier
                    .weight(1f)
                    .height(12.dp)
                    .background(Color(0xFFF18A74), RoundedCornerShape(6.dp)))
                Spacer(Modifier.width(6.dp))
                Box(Modifier
                    .weight(1f)
                    .height(12.dp)
                    .background(Color(0xFFE15757), RoundedCornerShape(6.dp)))
            }

            // Arrow indicator
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start) {
                Text(
                    "▲",
                    modifier = Modifier
                        .offset(x = 115.dp, y = (-13).dp)
                        .padding(start = 0.dp),
                    color = Color.Black,
                    fontSize = 20.sp)
            }
            Spacer(Modifier.height(2.dp))

            // HEART RATE INFO BOX
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Min)
                    .clip(RoundedCornerShape(18.dp))
                    .background(Color.White)
                    .border( // ✅
                        width = 1.dp,
                        color = Color(0xFFE8E8E8),
                        shape = RoundedCornerShape(18.dp)
                    )
            ) {
                Box(
                    modifier = Modifier
                        .width(10.dp)
                        .fillMaxHeight()
                        .background(
                            color = Color(0xFFA8E6A3),
                            shape = RoundedCornerShape(topStart = 18.dp, bottomStart = 18.dp)
                        )
                )

                Column(
                    modifier = Modifier.padding(18.dp)
                ) {

                    // --- TITLE ---
                    Text(
                        text = buildAnnotatedString {
                            withStyle(style = SpanStyle(color = Color(0xFF102A71))) {
                                append("Your Heart Rate : ")
                            }
                            withStyle(style = SpanStyle(color = Color(0xFF4CAF50))) {
                                append("79 BPM")
                            }
                        },
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(Modifier.height(10.dp))

                    // --- DESCRIPTION ---
                    Text(
                        text = buildAnnotatedString {
                            withStyle(style = SpanStyle(color = Color.Gray)) {
                                append("At 79 bpm, your heart rate is still within the healthy, ")
                            }
                            withStyle(style = SpanStyle(color = Color(0xFF4CAF50), fontWeight = FontWeight.SemiBold)) {
                                append("normal range.")
                            }
                        },
                        fontSize = 14.sp,
                        lineHeight = 21.sp
                    )

                    Spacer(Modifier.height(16.dp))

                    // --- NORMAL TAG ---
                    Box(
                        modifier = Modifier
                            .background(Color(0xFFA8E6A3), RoundedCornerShape(30.dp))
                            .padding(horizontal = 18.dp, vertical = 8.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(8.dp)
                                    .background(Color.White, CircleShape)
                            )
                            Spacer(Modifier.width(8.dp))
                            Text(
                                "Normal",
                                fontSize = 14.sp,
                                color = Color.White,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }
            }

            Spacer(Modifier.height(24.dp))

            // EXCLUSIVE ADVICE (COLLAPSIBLE)
            var isExpanded by remember { mutableStateOf(false) }

            Text("Exclusive Advice", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(16.dp))

            Column {
                if (isExpanded) {
                    AdviceItem(
                        title = "Stay Active",
                        description = "Engage in regular physical activities to support cardiovascular health. Aim for at least 150 minutes of moderate-intensity aerobic exercise or 75 minutes of vigorous-intensity exercise per week, along with muscle-strengthening activities.",
                        titleColor = Color(0xFF4CAF50)
                    )
                    AdviceItem(
                        title = "Monitor Your Heart Rate",
                        description = "Keep track of your heart rate during exercise and daily activities to ensure it stays within the normal range.",
                        titleColor = Color(0xFF4CAF50)
                    )
                    AdviceItem(
                        title = "Listen to Your Body",
                        description = "Pay attention to any changes in your heart rate or unusual symptoms during exercise or daily routines. If you experience any concerning symptoms, consult with a healthcare professional.",
                        titleColor = Color(0xFF4CAF50)
                    )
                    AdviceItem(
                        title = "Be Mindful of Heart Rate During Exercise",
                        description = "When engaging in physical activities, be aware of your target heart rate zone to ensure you are exercising at an appropriate intensity.",
                        titleColor = Color(0xFF4CAF50)
                    )
                    AdviceItem(
                        title = "Important Note",
                        description = "Remember, a normal heart rate is generally a positive indicator of cardiovascular health. However, it's essential to maintain a healthy lifestyle and listen to your body for any signs of potential issues. Regular medical check-ups and consultations with healthcare professionals can help ensure your heart health is well-maintained.",
                        titleColor = Color(0xFFE64A19)
                    )
                } else {
                    AdviceItem(
                        title = "Stay Active",
                        description = "Engage in regular physical activities to support cardiovascular health. Aim for at least 150 minutes of exercise weekly.",
                        titleColor = Color(0xFF4CAF50)
                    )
                }
            }


            Spacer(Modifier.height(4.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = if (isExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                    contentDescription = if (isExpanded) "Collapse Advice" else "Expand Advice",
                    tint = Color.Black,
                    modifier = Modifier
                        .size(36.dp)
                        .clickable { isExpanded = !isExpanded }
                )
            }




            Spacer(Modifier.height(32.dp))

            // STRESS LEVEL
            Text("Stress Level", fontSize = 18.sp, fontWeight = FontWeight.Bold)

            Spacer(Modifier.height(16.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Min)
                    .clip(RoundedCornerShape(18.dp))
                    .background(Color.White)
                    .border(
                        width = 1.dp,
                        color = Color(0xFFE8E8E8),
                        shape = RoundedCornerShape(18.dp)
                    )
            ) {
                Box(
                    modifier = Modifier
                        .width(10.dp)
                        .fillMaxHeight()
                        .background(
                            color = Color(0xFFA8E6A3),
                            shape = RoundedCornerShape(topStart = 18.dp, bottomStart = 18.dp)
                        )
                )

                Column(
                    modifier = Modifier.padding(18.dp)
                ) {

                    // --- TITLE ---
                    Text(
                        text = buildAnnotatedString {
                            withStyle(style = SpanStyle(color = Color(0xFF102A71))) {
                                append("Your Stress Level : ")
                            }
                            withStyle(style = SpanStyle(color = Color(0xFF4CAF50))) {
                                append("45 %")
                            }
                        },
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(Modifier.height(10.dp))

                    // --- DESCRIPTION ---
                    Text(
                        text = buildAnnotatedString {
                            withStyle(style = SpanStyle(color = Color.Gray)) {
                                append("At 32%, you are still within the ")
                            }
                            withStyle(style = SpanStyle(color = Color(0xFF4CAF50), fontWeight = FontWeight.SemiBold)) {
                                append("healthy range")
                            }
                            withStyle(style = SpanStyle(color = Color.Gray)) {
                                append(", your stress is manageable.")
                            }
                        },
                        fontSize = 14.sp,
                        lineHeight = 21.sp
                    )

                    Spacer(Modifier.height(16.dp))

                    // --- NORMAL TAG ---
                    Box(
                        modifier = Modifier
                            .background(Color(0xFFA8E6A3), RoundedCornerShape(30.dp))
                            .padding(horizontal = 18.dp, vertical = 8.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(8.dp)
                                    .background(Color.White, CircleShape)
                            )
                            Spacer(Modifier.width(8.dp))
                            Text(
                                "Normal",
                                fontSize = 14.sp,
                                color = Color.White,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }
            }

            Spacer(Modifier.height(30.dp))

            // SCIENTIFIC BASIS
            Text("Scientific Basis", fontSize = 18.sp, fontWeight = FontWeight.Bold)

            Spacer(Modifier.height(12.dp))

            Text(
                "According to a study published in the Journal of Stress and Health (2021), stress levels between 0% to 40% are considered within the healthy range for most individuals. The research suggests that maintaining stress levels below 40% helps prevent long-term negative impacts on physical and mental health, as the body's natural stress response remains manageable. Elevated stress above 40% can increase the risk of hypertension, anxiety, and other health complications. Regular monitoring of stress levels can assist in maintaining overall well-being.",
                fontSize = 13.sp,
                color = Color.DarkGray
            )

            Spacer(Modifier.height(100.dp))
        }
    }
}

@Composable
fun AdviceItem(title: String, description: String, titleColor: Color) {
    Row(modifier = Modifier.fillMaxWidth()) {

        Box(
            modifier = Modifier
                .padding(top = 6.dp)
                .size(8.dp)
                .background(titleColor, CircleShape)
        )

        Spacer(Modifier.width(12.dp))

        Column {
            Text(
                title,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = titleColor
            )

            Spacer(Modifier.height(4.dp))

            Text(
                description,
                fontSize = 14.sp,
                color = Color.DarkGray,
                lineHeight = 20.sp,
                textAlign = TextAlign.Justify
            )

            Spacer(Modifier.height(22.dp))
        }
    }
}
