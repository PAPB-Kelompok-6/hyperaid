package com.kelompok6.hyperaid.ui.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.kelompok6.hyperaid.ui.helper.AuthHelper
import com.kelompok6.hyperaid.ui.navigation.Routes
import androidx.navigation.compose.rememberNavController
import androidx.compose.ui.res.painterResource
import com.kelompok6.hyperaid.R
import androidx.compose.ui.layout.ContentScale
import androidx.compose.foundation.BorderStroke
import androidx.compose.ui.text.style.TextOverflow


//@Composable
//fun HomeScreen(navController: NavHostController) {
//    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
//        Text(text = "Home", style = MaterialTheme.typography.headlineSmall)
//    }
//}
@Composable
fun HomeScreen(navController: NavController) {
    val displayName by produceState(initialValue = "Loading...") {
        value = AuthHelper.getDisplayName()
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
            .padding(16.dp),
    ) {
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.hyperaid),
                    contentDescription = "Hyperaid logo",
                    modifier = Modifier
                        .widthIn(max = 180.dp)
                )
                IconButton(onClick = { navController.navigate(Routes.NOTIFICATION) }) {
                    Icon(
                        imageVector = Icons.Default.Notifications,
                        contentDescription = "Notifications",
                        tint = Color.Black
                    )
                }
            }
        }
        item {
            Text(
                text = "Good Morning, ${displayName}!",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.padding(top = 8.dp)
//                    .padding(top = (-8).dp)
            )
        }

        item {
            Box(modifier = Modifier.padding(top = 24.dp)) {
                HeartRateReminderCard(navController)
            }
        }

        item {
            Box(modifier = Modifier.padding(top = 16.dp)) {
                HeartRateCard(navController)
            }
        }

        item {
            Box(modifier = Modifier.padding(top = 16.dp)) {
                BloodPressureCard()
            }
        }

        item {
            Text(
                text = "Breaking News",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.padding(top = 24.dp)
            )
        }

        item {
            Box(modifier = Modifier.padding(top = 16.dp)) {
                NewsCard(
                    title = "Lifestyle Changes to Combat Hypertension",
                    subtitle = "Hypertension, or high blood pressure, is a major health concern...",
                    timeAgo = "17 hours ago",
                    imageRes = R.drawable.artikelsatu,
                    onClick = {
                        navController.navigate("article_screen/1")
                    }
                )
            }
        }

        item {
            Box(modifier = Modifier.padding(top = 12.dp)) {
                NewsCard(
                    title = "Hypertension and Heart Health: What You Need to Know",
                    subtitle = "",
                    timeAgo = "Yesterday",
                    imageRes = R.drawable.artikeldua,
                    fontSizeTitle = 16,
                    onClick = {
                        navController.navigate("article_screen/2")
                    }
                )
            }
        }

        item {
            Box(modifier = Modifier.padding(top = 12.dp)) {
                NewsCard(
                    title = "Tech Solutions for Hypertension Management",
                    subtitle = "",
                    timeAgo = "2 days ago",
                    imageRes = R.drawable.artikeltiga,
                    fontSizeTitle = 16,
                    onClick = {
                        navController.navigate("article_screen/3")
                    }
                )
            }
        }
    }
}


@Composable
fun HeartRateReminderCard(navController: NavController?) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .clickable { navController?.navigate(Routes.MEASURE_INSTRUCTION) },
        colors = CardDefaults.cardColors(containerColor = Color(0xFF2C2C2C)),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Notifications,
                    contentDescription = "Notification",
                    tint = Color.White
                )
                Text(
                    text = "Time to check your heart rate!",
                    color = Color.White,
                    fontSize = 16.sp
                )
            }
            Icon(
                imageVector = Icons.Default.ArrowForward,
                contentDescription = "Arrow",
                tint = Color.White
            )
        }
    }
}

@Composable
fun HeartRateCard(navController: NavController?) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFD85C5C)),
        shape = RoundedCornerShape(16.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Heart Rate",
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = "View More",
                        color = Color.White,
                        fontSize = 14.sp
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = "Heart",
                        tint = Color.White,
                        modifier = Modifier.size(48.dp)
                    )
                    Column {
                        Text(
                            text = "NA",
                            color = Color.White,
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "BPM",
                            color = Color.White,
                            fontSize = 14.sp
                        )
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Button(
                        onClick = { navController?.navigate(Routes.MEASURE_INSTRUCTION) },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2C2C2C)),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("Measure", color = Color.White)
                    }
                }
            }
            
            Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Clear,
                    contentDescription = "Chart",
                    tint = Color.White.copy(alpha = 0.3f),
                    modifier = Modifier.size(80.dp).padding(top = 16.dp)
                )
            }
        }
    }
}

@Composable
fun BloodPressureCard() {
    Column {
        Text(
            text = "Blood Pressure Reading",
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.Black,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    BloodPressureItem("100", "SYS", "mmHg", Modifier.weight(1f))
                    BloodPressureItem("56", "DIA", "mmHg", Modifier.weight(1f))
                    BloodPressureItem("45", "Pulse", "BPM", Modifier.weight(1f))
                }

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(12.dp)
                            .clip(CircleShape)
                            .background(Color.Gray)
                    )
                    Text(
                        text = "Connection not established",
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                }
            }
        }
    }
}

@Composable
fun BloodPressureItem(value: String, label: String, unit: String, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5)),
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
                color = Color.LightGray
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
fun NewsCard(
    title: String,
    subtitle: String,
    timeAgo: String,
    imageRes: Int? = null,
    fontSizeTitle: Int = 18,
    isFirst: Boolean = false,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        border = BorderStroke(0.8.dp, Color(0xFFE4E4E4))
    ) {

        Column(modifier = Modifier.padding(20.dp)) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {

                Column(modifier = Modifier.weight(1f)) {

                    // Logo + Name
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.logo_newscard),
                            contentDescription = "Hyperaid Logo",
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape)
                        )
                        Text(
                            text = "Hyperaid",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }

                    Spacer(Modifier.height(10.dp))

                    Text(
                        text = title,
                        fontSize = fontSizeTitle.sp,
                        fontWeight = FontWeight.SemiBold,
                        lineHeight = 22.sp,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )

                    if (isFirst) {
                        Spacer(Modifier.height(8.dp))
                        Text(
                            text = timeAgo,
                            fontSize = 13.sp,
                            color = Color.Gray
                        )
                    }
                }

                Spacer(Modifier.width(16.dp))

                Column(horizontalAlignment = Alignment.End) {
                    if (imageRes != null) {
                        Image(
                            painter = painterResource(id = imageRes),
                            contentDescription = null,
                            modifier = Modifier
                                .size(95.dp)
                                .clip(RoundedCornerShape(16.dp)),
                            contentScale = ContentScale.Crop
                        )
                    }

                    if (isFirst) {
                        Spacer(Modifier.height(12.dp))
                        Icon(
                            imageVector = Icons.Default.MoreHoriz,
                            contentDescription = "More",
                            tint = Color.Gray
                        )
                    }
                }
            }

            if (subtitle.isNotEmpty()) {
                Spacer(Modifier.height(16.dp))

                Text(
                    text = subtitle,
                    fontSize = 14.sp,
                    color = Color.Gray,
                    lineHeight = 20.sp,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            if (!isFirst) {
                Spacer(Modifier.height(12.dp))
                Text(
                    text = timeAgo,
                    fontSize = 13.sp,
                    color = Color.Gray
                )
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen(navController = rememberNavController())
}
