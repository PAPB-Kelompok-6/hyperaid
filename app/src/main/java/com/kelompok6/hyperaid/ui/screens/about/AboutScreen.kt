package com.kelompok6.hyperaid.ui.screens.about

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.LocalDrink
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.SmokingRooms
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.kelompok6.hyperaid.R
import com.kelompok6.hyperaid.data.enum.Gender
import com.kelompok6.hyperaid.ui.helper.AuthHelper
import com.kelompok6.hyperaid.ui.navigation.Routes
import com.kelompok6.hyperaid.ui.screens.start.OnboardingViewModel
import java.util.Locale

@Composable
fun AboutScreen(
    navController: NavHostController? = null,
    viewModel: OnboardingViewModel = viewModel()
) {
    var checking by remember { mutableStateOf(true) }

    // define the colors requested by user
    val selectedColor = Color(0xFFF6C9CB) // replaces dark purple
    val unselectedBg = Color.White // user asked white or lighter pink
    val selectedContentColor = Color(0xFF222222) // dark content on pink

    LaunchedEffect(Unit) {
        val uid = AuthHelper.getCurrentUser()?.uid
        if (uid != null) {
            val missing = viewModel.checkIfAboutIsMissing(uid)
            if (!missing && navController != null) {
                navController.navigate(Routes.HOME) {
                    popUpTo(Routes.LANGUAGE) { inclusive = true }
                }
                return@LaunchedEffect
            }
        }
        checking = false
    }
    if (checking) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        )
        return
    }

    val state by viewModel.state.collectAsState()
    LaunchedEffect(Unit) { viewModel.setAboutDefaultValues() }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(top = 40.dp, bottom = 120.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(80.dp))
            Text(
                text = "About you",
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.SemiBold),
                textAlign = TextAlign.Center
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = "Please provide some basic information about yourself to start using the app",
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 12.dp)
            )

            Spacer(Modifier.height(28.dp))

            // Gender selection row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Box(modifier = Modifier.weight(1f)) {
                    SelectCard(
                        label = "Male",
                        // using drawable pngs for active/inactive
                        painterResActive = R.drawable.male_inactive,
                        painterResInactive = R.drawable.male_inactive,
                        selected = state.gender == Gender.MALE,
                        onClick = { viewModel.update { it.copy(gender = Gender.MALE) } },
                        selectedColor = selectedColor,
                        unselectedBg = unselectedBg,
                        selectedContentColor = selectedContentColor
                    )
                }
                Box(modifier = Modifier.weight(1f)) {
                    SelectCard(
                        label = "Female",
                        painterResActive = R.drawable.female_inactive,
                        painterResInactive = R.drawable.female_inactive,
                        selected = state.gender == Gender.FEMALE,
                        onClick = { viewModel.update { it.copy(gender = Gender.FEMALE) } },
                        selectedColor = selectedColor,
                        unselectedBg = unselectedBg,
                        selectedContentColor = selectedContentColor
                    )
                }
            }

            Spacer(Modifier.height(28.dp))

            // Height
            LabeledSlider(
                title = "Height",
                value = state.height ?: 150.0,
                min = 50.0,
                max = 300.0,
                leadingRes = R.drawable.height_big,
                trailingRes = R.drawable.height_big,
                unit = "cm",
                onValueChange = { h -> viewModel.update { it.copy(height = h) } },
                activeColor = selectedColor,
                inactiveTrack = selectedColor.copy(alpha = 0.18f)
            )

            Spacer(Modifier.height(20.dp))

            // Weight
            LabeledSlider(
                title = "Weight",
                value = state.weight ?: 50.0,
                min = 20.0,
                max = 200.0,
                leadingRes = R.drawable.weight_mini,
                trailingRes = R.drawable.weight_big,
                unit = "kg",
                onValueChange = { w -> viewModel.update { it.copy(weight = w) } },
                activeColor = selectedColor,
                inactiveTrack = selectedColor.copy(alpha = 0.18f)
            )

            Spacer(Modifier.height(28.dp))

            // Smoking & Drinking
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Box(modifier = Modifier.weight(1f)) {
                    SelectCard(
                        label = "Smoking",
                        painterResActive = null,
                        painterResInactive = null,
                        icon = Icons.Filled.SmokingRooms,
                        selected = state.isSmoking == true,
                        onClick = {
                            val current = state.isSmoking == true
                            viewModel.update { it.copy(isSmoking = !current) }
                        },
                        selectedColor = selectedColor,
                        unselectedBg = unselectedBg,
                        selectedContentColor = selectedContentColor
                    )
                }
                Box(modifier = Modifier.weight(1f)) {
                    SelectCard(
                        label = "Drinking",
                        painterResActive = null,
                        painterResInactive = null,
                        icon = Icons.Filled.LocalDrink,
                        selected = state.isAlcoholic == true,
                        onClick = {
                            val current = state.isAlcoholic == true
                            viewModel.update { it.copy(isAlcoholic = !current) }
                        },
                        selectedColor = selectedColor,
                        unselectedBg = unselectedBg,
                        selectedContentColor = selectedContentColor
                    )
                }
            }

            Spacer(Modifier.height(32.dp))
        }

        // Continue FAB centered bottom - match HealthDisclaimer
        FloatingActionButton(
            onClick = {
                if (navController != null) {
                    val uid = AuthHelper.getCurrentUser()?.uid
                    if (uid != null) viewModel.saveAll(uid)

                    navController.navigate(Routes.HOME) {
                        popUpTo(Routes.ABOUT) { inclusive = true }
                        launchSingleTop = true
                    }
                }
            },
            containerColor = Color(0xFF222222),
            contentColor = Color.White,
            shape = CircleShape,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 70.dp)
                .size(48.dp)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = "Continue",
                modifier = Modifier.size(28.dp)
            )
        }
    }
}

@Composable
private fun SelectCard(
    label: String,
    icon: ImageVector? = null,
    // optional drawable resource ids for active/inactive PNGs
    painterResActive: Int? = null,
    painterResInactive: Int? = null,
    selected: Boolean,
    onClick: () -> Unit,
    // color overrides
    selectedColor: Color = MaterialTheme.colorScheme.primary,
    unselectedBg: Color = MaterialTheme.colorScheme.surface,
    selectedContentColor: Color = MaterialTheme.colorScheme.onPrimary
) {
    val bg = if (selected) selectedColor else unselectedBg
    val content = if (selected) selectedContentColor else MaterialTheme.colorScheme.onSurface
    val border = if (selected) BorderStroke(0.dp, Color.Transparent) else BorderStroke(1.dp, Color(0xFFE6E6E6))

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = bg),
        border = border,
        elevation = CardDefaults.cardElevation(defaultElevation = if (selected) 6.dp else 0.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // if drawable resource is provided, use it (active vs inactive), else fallback to vector icon
                if (painterResActive != null && painterResInactive != null) {
                    val res = if (selected) painterResActive else painterResInactive
                    Image(
                        painter = painterResource(id = res),
                        contentDescription = label,
                        modifier = Modifier.size(56.dp)
                    )
                } else if (icon != null) {
                    Icon(
                        imageVector = icon,
                        contentDescription = label,
                        tint = content,
                        modifier = Modifier.size(48.dp)
                    )
                }

                Spacer(Modifier.height(12.dp))
                Text(
                    text = label,
                    color = content,
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium)
                )
            }

            if (selected) {
                // small check badge top-right with white ring
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp)
                        .size(28.dp),
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(color = Color.White, shape = CircleShape)
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(3.dp)
                            .background(color = Color(0xFFD9534F), shape = CircleShape)
                    )

                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "Selected",
                        tint = Color.White,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .size(14.dp)
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LabeledSlider(
    title: String,
    value: Double,
    min: Double,
    max: Double,
    leadingIcon: ImageVector? = null,
    trailingIcon: ImageVector? = null,
    leadingRes: Int? = null,
    trailingRes: Int? = null,
    unit: String,
    onValueChange: (Double) -> Unit,
    activeColor: Color = MaterialTheme.colorScheme.primary,
    inactiveTrack: Color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f)
) {
    // icon size + spacer to compute alignment so the track lines up with the title
    val iconSize = 14.dp
    val spacer = 12.dp
    val trackStartPadding = iconSize + spacer

    Column(modifier = Modifier.fillMaxWidth().padding(start = 8.dp, end = 8.dp)) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            // leading icon: prefer drawable resource if provided, else ImageVector
            if (leadingRes != null) {
                Image(
                    painter = painterResource(id = leadingRes),
                    contentDescription = null,
                    modifier = Modifier.size(iconSize)
                )
            } else if (leadingIcon != null) {
                Icon(
                    imageVector = leadingIcon,
                    contentDescription = null,
                    tint = Color(0xFF444B5A),
                    modifier = Modifier.size(iconSize)
                )
            }

            Spacer(Modifier.width(spacer))

            Slider(
                value = value.toFloat(),
                onValueChange = { onValueChange(it.toDouble()) },
                valueRange = min.toFloat()..max.toFloat(),
                modifier = Modifier
                    .weight(1f)
                    .height(12.dp),
                colors = SliderDefaults.colors(
                    thumbColor = activeColor,
                    activeTrackColor = activeColor,
                    inactiveTrackColor = inactiveTrack
                ),
                interactionSource = remember { MutableInteractionSource() }
            )

            Spacer(Modifier.width(spacer))

            // trailing icon: prefer drawable resource if provided, else ImageVector
            if (trailingRes != null) {
                Image(
                    painter = painterResource(id = trailingRes),
                    contentDescription = null,
                    modifier = Modifier.size(iconSize)
                )
            } else if (trailingIcon != null) {
                Icon(
                    imageVector = trailingIcon,
                    contentDescription = null,
                    tint = Color(0xFF444B5A),
                    modifier = Modifier.size(iconSize)
                )
            }
        }

        Spacer(Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth().padding(start = trackStartPadding, end = trackStartPadding),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // smaller min/max labels
            Text(text = "${min.toInt()}$unit", style = MaterialTheme.typography.labelSmall, color = Color.Gray)
            Text(
                text = String.format(Locale.getDefault(), "%.0f %s", value, unit),
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
            )
            Text(text = "${max.toInt()}$unit", style = MaterialTheme.typography.labelSmall, color = Color.Gray)
        }
    }
}
