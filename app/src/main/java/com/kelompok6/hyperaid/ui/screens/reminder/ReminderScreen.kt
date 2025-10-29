package com.kelompok6.hyperaid.ui.screens.reminder

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.gestures.snapping.SnapPosition
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.kelompok6.hyperaid.R
import kotlinx.coroutines.launch

@Composable
fun ReminderScreen(navController: NavHostController) {
    var showCards by remember { mutableStateOf(false) }
    var offsetY by remember { mutableStateOf(0f) } // posisi geser vertikal
    val dragSensitivity = 1f
    val coroutineScope = rememberCoroutineScope()

    Box (
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        Column (
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ){
            Text(text = "Reminder", fontSize = 24.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily.SansSerif)

            Image(
                painter = painterResource( R.drawable.jam_merah),
                contentDescription = "Alarm",
                modifier = Modifier.size(400.dp)
            )

            Text(text = "A little reminder for you!", fontSize = 18.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily.SansSerif)

            Spacer(Modifier.height(20.dp))

            Text(text = "Never miss a health check-up again! Use this feature to keep track of all your appointments.",
                Modifier.padding(horizontal = 40.dp), lineHeight = 22.5.sp, fontSize = 14.sp, fontFamily = FontFamily.SansSerif, textAlign = TextAlign.Center)
        }

        // ➕ FAB di kanan bawah
        FloatingActionButton(
//            onClick = { showSheet = true },
            onClick = {showCards = !showCards},
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(10.dp)
                .clip(CircleShape),
            containerColor = Color.Black
        ) {
            Icon(Icons.Default.Add, contentDescription = "Add Reminder", tint = Color.White)
        }


        AnimatedVisibility(
            visible = showCards,
            enter = slideInVertically(initialOffsetY = { it / 2 }) + fadeIn(),
            exit = slideOutVertically(targetOffsetY = { it / 2 }) + fadeOut(),
            modifier = Modifier.align(Alignment.BottomCenter)
        ) {
            Surface(
                shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
                shadowElevation = 10.dp,
                tonalElevation = 5.dp,
                color = Color.White,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(390.dp)
                    .offset { IntOffset(0, offsetY.toInt()) }
                    .draggable(
                        orientation = Orientation.Vertical,
                        state = rememberDraggableState { delta ->
                            offsetY = (offsetY + delta * dragSensitivity).coerceAtLeast(0f)
                        },
                        onDragStopped = {
                            // Jika digeser cukup jauh, tutup
                            if (offsetY > 250f) {
                                coroutineScope.launch {
                                    offsetY = 0f
                                    showCards = false
                                }
                            } else {
                                // Balik ke posisi awal
                                coroutineScope.launch {
                                    offsetY = 0f
                                }
                            }
                        }
                    )
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(20.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .width(40.dp)
                            .height(5.dp)
                            .clip(RoundedCornerShape(50))
                            .background(Color.LightGray)
                    )

                    Text(
                        "Remind Me to Record",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                    Spacer(modifier = Modifier.height(20.dp))

                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        item { ReminderCard("Weight & BMI", R.drawable.timbangan) }
                        item { ReminderCard("Blood Pressure", R.drawable.sfigmomanometer) }
                        item { ReminderCard("Heart Rate", R.drawable.hati) }
                        item { ReminderCard("Work Out", R.drawable.damburu) }
                    }
                }
            }
        }
    }
}
@Composable
fun ReminderCard(title: String, imageRes: Int) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(130.dp)
            .clickable { /* TODO: open detail or form */ },
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 12.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(15.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,

        ) {
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = title,
                modifier = Modifier.size(70.dp)

            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(title, textAlign = TextAlign.Center, fontSize = 14.sp)
        }
    }
}