package com.kelompok6.hyperaid.ui.screens.vitalsync

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun VitalsyncAddNotesScreen() {
    var selectedSymptom by remember { mutableStateOf("") }
    var selectedSymptomDuration by remember { mutableStateOf("") }
    var selectedSeverity by remember { mutableStateOf("") }
    var selectedFrequency by remember { mutableStateOf("") }
    var selectedActivity by remember { mutableStateOf("") }
    var selectedActivityDuration by remember { mutableStateOf("") }
    var selectedIntensity by remember { mutableStateOf("") }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        item {
            SectionHeader("Symptoms")
        }

        item {
            DropdownField(
                label = "Symptoms:",
                selectedValue = selectedSymptom,
                onValueChange = { selectedSymptom = it }
            )
        }

        item {
            DropdownField(
                label = "Duration of Symptoms:",
                selectedValue = selectedSymptomDuration,
                onValueChange = { selectedSymptomDuration = it }
            )
        }

        item {
            DropdownField(
                label = "Severity Level:",
                selectedValue = selectedSeverity,
                onValueChange = { selectedSeverity = it }
            )
        }

        item {
            DropdownField(
                label = "Frequency:",
                selectedValue = selectedFrequency,
                onValueChange = { selectedFrequency = it }
            )
        }

        item {
            Spacer(modifier = Modifier.height(4.dp))
        }

        item {
            SectionHeader("Activity")
        }

        item {
            DropdownField(
                label = "Activity:",
                selectedValue = selectedActivity,
                onValueChange = { selectedActivity = it }
            )
        }

        item {
            DropdownField(
                label = "Duration of Activity:",
                selectedValue = selectedActivityDuration,
                onValueChange = { selectedActivityDuration = it }
            )
        }

        item {
            DropdownField(
                label = "Intensity:",
                selectedValue = selectedIntensity,
                onValueChange = { selectedIntensity = it }
            )
        }

        item {
            DropdownField(
                label = "Food and Drink:",
                selectedValue = selectedIntensity,
                onValueChange = { selectedIntensity = it }
            )
        }

        item {
            DropdownField(
                label = "Stress:",
                selectedValue = selectedIntensity,
                onValueChange = { selectedIntensity = it }
            )
        }

        item {
            Spacer(modifier = Modifier.height(4.dp))
        }

        item {
            Button(
                onClick = { /* Handle save button click */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp)
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2C2C2C)),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(
                    text = "SAVE",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.White
                )
            }
        }

        item {
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun SectionHeader(title: String) {
    Text(
        text = title,
        fontSize = 24.sp,
        fontWeight = FontWeight.SemiBold,
        color = Color.Black
    )
}

@Composable
fun DropdownField(
    label: String,
    selectedValue: String,
    onValueChange: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = label,
            fontSize = 14.sp,
            color = Color.Black.copy(alpha = 0.7f)
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    width = 1.dp,
                    color = Color.Gray.copy(alpha = 0.3f),
                    shape = RoundedCornerShape(8.dp)
                )
                .background(Color.White, RoundedCornerShape(8.dp))
                .clickable { expanded = true }
                .padding(horizontal = 16.dp, vertical = 14.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = selectedValue.ifEmpty { "Select" },
                    fontSize = 14.sp,
                    color = if (selectedValue.isEmpty()) Color.Gray else Color.Black
                )
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = "Dropdown",
                    tint = Color.Gray
                )
            }

            // Dropdown Menu (simplified - you'd populate with actual options)
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .background(Color.White)
            ) {
                // Sample dropdown items
                val items = when (label) {
                    "Symptoms:" -> listOf("Headache", "Nausea", "Fatigue", "Dizziness", "Chest Pain")
                    "Duration of Symptoms:" -> listOf("Less than 1 hour", "1-3 hours", "3-6 hours", "6-12 hours", "More than 12 hours")
                    "Severity Level:" -> listOf("Mild", "Moderate", "Severe", "Very Severe")
                    "Frequency:" -> listOf("Once", "Occasionally", "Frequently", "Constantly")
                    "Activity:" -> listOf("Walking", "Running", "Cycling", "Swimming", "Gym Workout")
                    "Duration of Activity:" -> listOf("Less than 15 min", "15-30 min", "30-60 min", "1-2 hours", "More than 2 hours")
                    "Intensity:" -> listOf("Light", "Moderate", "Vigorous", "Very Intense")
                    "Food and Drink:" -> listOf("null", "null", "null", "null")
                    "Stress:" -> listOf("null", "null", "null", "null", "null", "null")
                    else -> listOf()
                }

                items.forEach { item ->
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = item,
                                fontSize = 14.sp
                            )
                        },
                        onClick = {
                            onValueChange(item)
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DropdownFieldPreview() {
    MaterialTheme {
        VitalsyncAddNotesScreen()
    }
}