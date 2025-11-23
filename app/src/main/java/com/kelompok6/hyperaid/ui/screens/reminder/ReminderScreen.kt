package com.kelompok6.hyperaid.ui.screens.reminder

import android.app.TimePickerDialog
import android.content.Context
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.activity.viewModels
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.kelompok6.hyperaid.R
import com.kelompok6.hyperaid.data.database.ReminderDatabase
import com.kelompok6.hyperaid.data.model.Reminder
import com.kelompok6.hyperaid.data.repository.ReminderRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalTime
import kotlin.getValue

@Composable
fun ReminderScreen(navController: NavHostController) {
    val context = LocalContext.current

    // ✅ Inisialisasi Room + Repository + ViewModel
    val database = remember { ReminderDatabase.getDatabase(context) }
    val repository = remember { ReminderRepository(database.reminderDAO()) }
    val factory = remember { ReminderViewModelFactory(repository) }
    val viewModel: ReminderViewModel = viewModel(factory = factory)

    var showCards by remember { mutableStateOf(false) }
    var selectedReminder by remember { mutableStateOf<String?>(null) }
    var editingReminder by remember { mutableStateOf<Reminder?>(null) }
    val reminders by viewModel.reminders.collectAsState(initial = emptyList())

    Box(modifier = Modifier.fillMaxSize()) {

        // Jika belum ada reminder
        if (reminders.isEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = "Reminder", fontSize = 24.sp, fontWeight = FontWeight.Bold)
                Image(
                    painter = painterResource(R.drawable.jam_merah),
                    contentDescription = "Alarm",
                    modifier = Modifier.size(300.dp)
                )
                Spacer(Modifier.height(16.dp))
                Text("A little reminder for you!", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Spacer(Modifier.height(8.dp))
                Text(
                    "Never miss a health check-up again! Use this feature to keep track of all your appointments.",
                    textAlign = TextAlign.Center,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(horizontal = 32.dp)
                )
            }
        } else {
            // Jika ada list reminder
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(reminders) { reminder ->
                    ReminderList(
                        reminder = reminder,
                        onEdit = { editingReminder = reminder },
                        onDelete = { viewModel.deleteReminder(reminder) },
                        onActive = { rem, isActive ->
                            viewModel.updateReminder(rem.copy(isActive = isActive))
                        }
                    )
                }
            }
        }

        // Tombol tambah reminder
        FloatingActionButton(
            onClick = { showCards = !showCards },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(20.dp)
                .clip(CircleShape),
            containerColor = Color.Black
        ) {
            Icon(Icons.Default.Add, contentDescription = "Add Reminder", tint = Color.White)
        }

        // Bottom Sheet Pilihan Reminder
        DraggableBottomSheet(
            visible = showCards,
            onDismiss = { showCards = false }
        ) {
            Box(
                modifier = Modifier
                    .width(40.dp)
                    .height(5.dp)
                    .clip(RoundedCornerShape(50))
                    .background(Color.LightGray)
            )
            Text("Remind Me to Record", fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Spacer(Modifier.height(20.dp))

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(18.dp)
            ) {
                item { ReminderCard("Weight & BMI", R.drawable.timbangan) { selectedReminder = it } }
                item { ReminderCard("Blood Pressure", R.drawable.sfigmomanometer) { selectedReminder = it } }
                item { ReminderCard("Heart Rate", R.drawable.hati) { selectedReminder = it } }
                item { ReminderCard("Work Out", R.drawable.damburu) { selectedReminder = it } }
            }
        }

        // Bottom Sheet Record Reminder
        DraggableBottomSheet(
            visible = selectedReminder != null,
            onDismiss = { selectedReminder = null }
        ) {
            Box(
                modifier = Modifier
                    .width(40.dp)
                    .height(5.dp)
                    .clip(RoundedCornerShape(50))
                    .background(Color.LightGray)
            )
            Spacer(modifier = Modifier.height(20.dp))
            selectedReminder?.let { title ->
                RecordReminder(
                    reminderTitle = title,
                    onSave = { reminder ->
                        viewModel.addReminder(reminder)
                        selectedReminder = null
                        showCards = false
                    }
                )
            }
        }

        DraggableBottomSheet(
            visible = editingReminder != null,
            onDismiss = { editingReminder = null }
        ) {
            Box(
                modifier = Modifier
                    .width(40.dp)
                    .height(5.dp)
                    .clip(RoundedCornerShape(50))
                    .background(Color.LightGray)
            )
            Spacer(modifier = Modifier.height(20.dp))

            editingReminder?.let { reminderToEdit ->
                EdithReminder(
                    reminderTitle = reminderToEdit.title,
                    existingReminder = reminderToEdit, // kirim data lama
                    onSave = { updated ->
                        viewModel.updateReminder(updated)
                        editingReminder = null
                    }
                )
            }
        }
    }
}

@Composable
fun ReminderCard(title: String, imageRes: Int, onClick: (String) -> Unit) {
    // Fungsi buat nampilin pilihan reminder
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
            .height(130.dp)
            .clickable { onClick(title) },
        shape = RoundedCornerShape(16.dp),
//        elevation = CardDefaults.cardElevation(defaultElevation = 12.dp)
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

@Composable
fun ReminderList(
    reminder: Reminder,
    onEdit: () -> Unit,
    onDelete: () -> Unit,
    onActive: (Reminder, Boolean) -> Unit
) {
    // Fungsi buat namppilin list reminder (kalau ada)
     var nyalaGak by remember { mutableStateOf(reminder.isActive) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                // .clickable { onCheckedChange(reminder.isActive)}
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            // horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(Modifier.weight(1f)) {
                Text(reminder.title, fontWeight = FontWeight.Bold)
                Text(reminder.time, fontSize = 20.sp)
                Text(reminder.days.joinToString(", "), fontSize = 12.sp)
            }
             Spacer(modifier = Modifier.width(12.dp))
             IconButton(onClick = onEdit) {
                 Icon(Icons.Default.Edit, contentDescription = "Edit", modifier = Modifier.size(20.dp))
             }
            Spacer(modifier = Modifier.width(8.dp))
            IconButton(onClick = onDelete) {
                Icon(Icons.Default.Delete, contentDescription = "Delete", modifier = Modifier.size(20.dp))
            }
            Spacer(modifier = Modifier.width(12.dp))
            Switch(
                checked = nyalaGak,
                onCheckedChange = {
                    nyalaGak = it
                    onActive(reminder, it)
                }
            )
        }
    }
}

@Composable
fun DraggableBottomSheet(
    // Fungsi buat munculin surface
    visible: Boolean,
    onDismiss: () -> Unit,
    content: @Composable ColumnScope.() -> Unit
) {
    if (!visible) return

    var offsetY by remember { mutableFloatStateOf(0f) }
    val coroutineScope = rememberCoroutineScope()

    // Biar bisa balik tanpa scroll
    BackHandler(
        enabled = true,
        onBack = { coroutineScope.launch { onDismiss() } }
    )

    Box(modifier = Modifier.fillMaxSize()) {
        AnimatedVisibility(
            visible = visible,
            enter = slideInVertically(initialOffsetY = { it / 2 }) + fadeIn(),
            exit = slideOutVertically(targetOffsetY = { it / 2 }) + fadeOut(),
            modifier = Modifier.align(Alignment.BottomCenter)
        ) {
            Surface(
                shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
//                shadowElevation = 10.dp,
                color = Color.White,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(390.dp)
                    .offset { IntOffset(0, offsetY.toInt()) }
                    // Balik dengan scroll
                    .draggable(
                        orientation = Orientation.Vertical,
                        state = rememberDraggableState { delta ->
                            offsetY = (offsetY + delta).coerceAtLeast(0f)
                        },
                        onDragStopped = {
                            if (offsetY > 200f) {
                                coroutineScope.launch {
                                    offsetY = 0f
                                    onDismiss()
                                }
                            } else {
                                coroutineScope.launch { offsetY = 0f }
                            }
                        }
                    )
            ) {
                Column(
                    content = content,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(20.dp)
                )
            }
        }
    }
}

@Composable
fun RecordReminder(reminderTitle: String, onSave: (Reminder) -> Unit) {
    // Fungsi buat set hari dan tanggal reminder
    val context = LocalContext.current
    var selectedTime by remember { mutableStateOf(LocalTime.now()) }
    val selectedDays = remember { mutableStateListOf<String>() }
    var showTimePicker by remember { mutableStateOf(false) }

    Text("Reminder for $reminderTitle", fontWeight = FontWeight.Bold, fontSize = 18.sp)
    Spacer(Modifier.height(30.dp))

    Box(
        modifier = Modifier
            .border(1.dp, Color.Gray, RoundedCornerShape(8.dp))
            .padding(12.dp)
            .clickable { showTimePicker = true }
    ) {
        Text(
            "${selectedTime.hour.toString().padStart(2, '0')}:${selectedTime.minute.toString().padStart(2, '0')}",
            fontSize = 22.sp
        )
    }

    if (showTimePicker) {
        // Ini digunakan buat milih jam reminder
        val timePicker = TimePickerDialog(
            context,
            { _, hour, minute ->
                selectedTime = LocalTime.of(hour, minute)
                showTimePicker = false
            },
            selectedTime.hour,
            selectedTime.minute,
            true
        )
        timePicker.show()
    }

    Spacer(Modifier.height(30.dp))
    Text("Repeat", fontWeight = FontWeight.Bold, fontSize = 16.sp)
    Spacer(Modifier.height(20.dp))

    val days = listOf("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat")

    // Pilih hari reminder
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier.fillMaxWidth()
    ) {
        days.forEach { day ->
            val selected = selectedDays.contains(day)
            Surface(
                shape = RoundedCornerShape(10.dp),
                color = if (selected) Color(0xFFF6C9CB) else Color(0xFFF2F2F2),
                modifier = Modifier
                    .size(40.dp)
                    .clickable {
                        if (selected) selectedDays.remove(day)
                        else selectedDays.add(day)
                    }
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(day, fontSize = 12.sp, fontWeight = FontWeight.Medium)
                }
            }
        }
    }

    Spacer(Modifier.height(30.dp))

    Button(
        // Tombol simpan reminder
        onClick = {
            onSave(
                Reminder(
                    title = reminderTitle,
                    time = "${selectedTime.hour}:${selectedTime.minute.toString().padStart(2, '0')}",
                    days = selectedDays,
                    isActive = true
                )
            )
            Toast.makeText(
                context,
                "Reminder $reminderTitle diset pada ${selectedDays.joinToString()} jam ${selectedTime.hour}:${selectedTime.minute}",
                Toast.LENGTH_LONG
            ).show()
        },
        enabled = selectedDays.isNotEmpty(),
        colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
        modifier = Modifier.fillMaxWidth().height(50.dp)
    ) {
        Text("SAVE", color = Color.White, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun EdithReminder(
    reminderTitle: String,
    existingReminder: Reminder? = null, // ✅ opsional
    onSave: (Reminder) -> Unit
) {
    val context = LocalContext.current
    var selectedTime by remember {
        mutableStateOf(
            existingReminder?.let {
                val (hour, minute) = it.time.split(":").map(String::toInt)
                LocalTime.of(hour, minute)
            } ?: LocalTime.now()
        )
    }

    val selectedDays = remember {
        mutableStateListOf<String>().apply {
            existingReminder?.days?.let { addAll(it) }
        }
    }

    var showTimePicker by remember { mutableStateOf(false) }

    Text("Reminder for $reminderTitle", fontWeight = FontWeight.Bold, fontSize = 18.sp)
    Spacer(Modifier.height(30.dp))

    Box(
        modifier = Modifier
            .border(1.dp, Color.Gray, RoundedCornerShape(8.dp))
            .padding(12.dp)
            .clickable { showTimePicker = true }
    ) {
        Text(
            "${selectedTime.hour.toString().padStart(2, '0')}:${selectedTime.minute.toString().padStart(2, '0')}",
            fontSize = 22.sp
        )
    }

    if (showTimePicker) {
        val timePicker = TimePickerDialog(
            context,
            { _, hour, minute ->
                selectedTime = LocalTime.of(hour, minute)
                showTimePicker = false
            },
            selectedTime.hour, selectedTime.minute, true
        )
        timePicker.show()
    }

    Spacer(Modifier.height(30.dp))
    Text("Repeat", fontWeight = FontWeight.Bold, fontSize = 16.sp)
    Spacer(Modifier.height(20.dp))

    val days = listOf("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat")
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier.fillMaxWidth()
    ) {
        days.forEach { day ->
            val selected = selectedDays.contains(day)
            Surface(
                shape = RoundedCornerShape(10.dp),
                color = if (selected) Color(0xFFF6C9CB) else Color(0xFFF2F2F2),
                modifier = Modifier
                    .size(40.dp)
                    .clickable {
                        if (selected) selectedDays.remove(day)
                        else selectedDays.add(day)
                    }
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(day, fontSize = 12.sp, fontWeight = FontWeight.Medium)
                }
            }
        }
    }

    Spacer(Modifier.height(30.dp))

    Button(
        onClick = {
            onSave(
                Reminder(
                    id = existingReminder?.id ?: 0, // ✅ pakai ID lama kalau edit
                    title = reminderTitle,
                    time = "${selectedTime.hour}:${selectedTime.minute.toString().padStart(2, '0')}",
                    days = selectedDays,
                    isActive = existingReminder?.isActive ?: true
                )
            )
            Toast.makeText(
                context,
                if (existingReminder == null)
                    "Reminder $reminderTitle diset."
                else
                    "Reminder $reminderTitle diperbarui.",
                Toast.LENGTH_LONG
            ).show()
        },
        enabled = selectedDays.isNotEmpty(),
        colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
        modifier = Modifier.fillMaxWidth().height(50.dp)
    ) {
        Text(
            if (existingReminder == null) "SAVE" else "UPDATE",
            color = Color.White,
            fontWeight = FontWeight.Bold
        )
    }
}