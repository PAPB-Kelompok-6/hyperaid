package com.kelompok6.hyperaid.ui.screens.fitsync.nutritrack

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.kelompok6.hyperaid.R

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun NutritrackAddScreen(navController: NavController? = null) {
    var foodName by remember { mutableStateOf("") }
    var selectedMealTime by remember { mutableStateOf("") }
    var portionAmount by remember { mutableStateOf(1) }
    var remindMe by remember { mutableStateOf(false) }
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Top Bar
        TopAppBar(
            title = {
                Text(
                    text = "Morning NutriTrack",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black
                )
            },
            navigationIcon = {
                IconButton(onClick = { navController?.popBackStack() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.Black
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.White
            )
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(16.dp))
            }

            // Plate illustration
            item {
                Box(
                    modifier = Modifier
                        .size(200.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.food_served),
                        contentDescription = "food_served",
                        modifier = Modifier.scale(3.5f)
                    )
                }
            }

            item {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "What did you eat today?",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.Black
                    )
                    Text(
                        text = "Don't forget to log your meals so FitSync can\ntrack your daily nutrition.",
                        fontSize = 14.sp,
                        color = Color.Gray,
                        textAlign = TextAlign.Center,
                        lineHeight = 20.sp
                    )
                }
            }

            item {
                InputField(
                    label = "Food Name",
                    value = foodName,
                    onValueChange = { foodName = it },
                    placeholder = "Food name"
                )
            }

            item {
                DropdownInputField(
                    label = "Meal Time",
                    selectedValue = selectedMealTime,
                    placeholder = "Select",
                    expanded = expanded,
                    onExpandedChange = { expanded = it },
                    onValueChange = {
                        selectedMealTime = it
                        expanded = false
                    },
                    items = listOf("Breakfast", "Morning Snack", "Lunch", "Afternoon Snack", "Dinner", "Evening Snack")
                )
            }

            item {
                PortionSelector(
                    amount = portionAmount,
                    onAmountChange = { portionAmount = it }
                )
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    Checkbox(
                        checked = remindMe,
                        onCheckedChange = { remindMe = it },
                        colors = CheckboxDefaults.colors(
                            checkedColor = Color(0xFFD85C5C),
                            uncheckedColor = Color.Gray
                        )
                    )
                    Text(
                        text = "Remind me to record my meals regularly.",
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                }
            }

            item {
                Button(
                    onClick = { /* TODO: save action */ },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2C2C2C)),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = "Save and Track Nutrition",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.White
                    )
                }
            }

            item {
                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}

@Composable
fun InputField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = label,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = Color.Black
        )

        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            placeholder = {
                Text(
                    text = placeholder,
                    color = Color.Gray,
                    fontSize = 14.sp
                )
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.Gray.copy(alpha = 0.5f),
                unfocusedBorderColor = Color.Gray.copy(alpha = 0.3f),
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White
            ),
            shape = RoundedCornerShape(8.dp)
        )
    }
}

@Composable
fun DropdownInputField(
    label: String,
    selectedValue: String,
    placeholder: String,
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    onValueChange: (String) -> Unit,
    items: List<String>
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = label,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = Color.Black
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
                .clickable { onExpandedChange(true) }
                .padding(horizontal = 16.dp, vertical = 16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = selectedValue.ifEmpty { placeholder },
                    fontSize = 14.sp,
                    color = if (selectedValue.isEmpty()) Color.Gray else Color.Black
                )
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = "Dropdown",
                    tint = Color.Gray
                )
            }

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { onExpandedChange(false) },
                modifier = Modifier
                    .fillMaxWidth(0.87f)
                    .background(Color.White)
            ) {
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
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun PortionSelector(
    amount: Int,
    onAmountChange: (Int) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "Amount (Portion)",
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = Color.Black
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = { if (amount > 1) onAmountChange(amount - 1) },
                modifier = Modifier
                    .size(40.dp)
                    .border(1.dp, Color.Gray.copy(alpha = 0.3f), CircleShape)
            ) {
                Icon(
                    imageVector = Icons.Default.Remove,
                    contentDescription = "Decrease",
                    tint = Color.Gray
                )
            }

            Text(
                text = amount.toString(),
                fontSize = 24.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black
            )

            IconButton(
                onClick = { onAmountChange(amount + 1) },
                modifier = Modifier
                    .size(40.dp)
                    .border(1.dp, Color.Gray.copy(alpha = 0.3f), CircleShape)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Increase",
                    tint = Color.Gray
                )
            }
        }
    }
}