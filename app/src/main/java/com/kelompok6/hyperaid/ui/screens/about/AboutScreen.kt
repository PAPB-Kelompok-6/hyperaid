package com.kelompok6.hyperaid.ui.screens.about

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.LocalDrink
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.SmokingRooms
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.kelompok6.hyperaid.data.enum.Gender
import com.kelompok6.hyperaid.ui.helper.AuthHelper
import com.kelompok6.hyperaid.ui.navigation.Routes
import com.kelompok6.hyperaid.ui.screens.start.OnboardingViewModel

@Composable
fun AboutScreen(
    navController: NavHostController? = null,
    viewModel: OnboardingViewModel = viewModel()
) {
    var checking by remember { mutableStateOf(true) }

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
                .background(Color.White)
        )
        return
    }

    val state by viewModel.state.collectAsState()
    LaunchedEffect(Unit) {
        viewModel.setAboutDefaultValues()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp)
    ) {
        // konten utama
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(top = 60.dp, bottom = 175.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "About You",
                style = MaterialTheme.typography.headlineSmall,
                textAlign = TextAlign.Center
            )
            Spacer(Modifier.height(12.dp))
            Text(
                text = "Please provide some basic information about yourself to start using the app",
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center
            )

            GenderSelector(
                selectedGender = state.gender ?: Gender.MALE,
                onSelected = { g -> viewModel.update { it.copy(gender = g) } }
            )

            HeightSlider(
                value = state.height ?: 150.0,
                onValueChange = { h -> viewModel.update { it.copy(height = h) } }
            )

            WeightSlider(
                value = state.weight ?: 50.0,
                onValueChange = { w -> viewModel.update { it.copy(weight = w) } }
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.padding(top = 32.dp, bottom = 24.dp)
            ) {
                IsSmokingButton(
                    isSmoking = state.isSmoking ?: false,
                    onSelected = { s -> viewModel.update { it.copy(isSmoking = s) } }
                )
                IsAlcoholicButton(
                    isAlcoholic = state.isAlcoholic ?: false,
                    onSelected = { a -> viewModel.update { it.copy(isAlcoholic = a) } }
                )
            }
        }

        // FAB di bawah tengah, naik 100.dp dari bawah
        FloatingActionButton(
            onClick = {
                if (navController != null) {
                    // TODO: chain to next preferences screen (if any)
                    // currently moved on to home
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
                .padding(bottom = 75.dp)
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
fun GenderSelector(selectedGender: Gender?, onSelected: (Gender) -> Unit) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.padding(top = 32.dp, bottom = 24.dp)
    ) {
        GenderCard(
            label = "Male",
            icon = Icons.Default.Person,
            selected = selectedGender == Gender.MALE,
            onClick = { onSelected(Gender.MALE) }
        )
        GenderCard(
            label = "Female",
            icon = Icons.Default.Face,
            selected = selectedGender == Gender.FEMALE,
            onClick = { onSelected(Gender.FEMALE) }
        )
    }
}

@Composable
fun GenderCard(
    label: String,
    icon: ImageVector,
    selected: Boolean,
    onClick: () -> Unit
) {
    val bgColor = if (selected) Color(0xFF444444) else Color(0xFFDDDDDD)
    val contentColor = if (selected) Color.White else Color.Black

    Card(
        modifier = Modifier
            .size(120.dp, 140.dp)
            .clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = bgColor),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                icon,
                contentDescription = label,
                tint = contentColor,
                modifier = Modifier.size(48.dp)
            )
            Spacer(Modifier.height(8.dp))
            Text(label, color = contentColor)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HeightSlider(
    value: Double,
    onValueChange: (Double) -> Unit,
    modifier: Modifier = Modifier
) {
    val min = 50.0
    val max = 300.0

    val interaction =
        remember { MutableInteractionSource() }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Height",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Left
        )

        Spacer(Modifier.height(24.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {

            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "Short",
                tint = Color.Gray,
                modifier = Modifier
                    .size(32.dp)
                    .weight(0.1f)
            )

            Slider(
                value = value.toFloat(),
                onValueChange = { onValueChange(it.toDouble()) },
                valueRange = min.toFloat()..max.toFloat(),
                modifier = Modifier.weight(0.8f),
                interactionSource = interaction,
                colors = SliderDefaults.colors(
                    thumbColor = Color.Gray,
                    activeTrackColor = Color.Gray,
                    inactiveTrackColor = Color.LightGray
                ),
                thumb = {
                    Box(
                        modifier = Modifier
                            .size(22.dp)
                            .border(
                                width = 6.dp,
                                color = Color.Gray,
                                shape = CircleShape
                            )
                    )
                }
            )

            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "Tall",
                tint = Color.Gray,
                modifier = Modifier
                    .size(48.dp)
                    .weight(0.1f)
            )
        }

        Spacer(Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "${min.toInt()} cm",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )
            Text(
                text = String.format("%.2f cm", value),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "${max.toInt()} cm",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeightSlider(
    value: Double,
    onValueChange: (Double) -> Unit,
    modifier: Modifier = Modifier
) {
    val min = 20.0
    val max = 200.0

    val interaction =
        remember { MutableInteractionSource() }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Weight",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Left
        )

        Spacer(Modifier.height(24.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {

            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "Small",
                tint = Color.Gray,
                modifier = Modifier
                    .size(32.dp)
                    .weight(0.1f)
            )

            Slider(
                value = value.toFloat(),
                onValueChange = { onValueChange(it.toDouble()) },
                valueRange = min.toFloat()..max.toFloat(),
                modifier = Modifier.weight(0.8f),
                interactionSource = interaction,
                colors = SliderDefaults.colors(
                    thumbColor = Color.Gray,
                    activeTrackColor = Color.Gray,
                    inactiveTrackColor = Color.LightGray
                ),
                thumb = {
                    Box(
                        modifier = Modifier
                            .size(22.dp)
                            .border(
                                width = 6.dp,
                                color = Color.Gray,
                                shape = CircleShape
                            )
                    )
                }
            )

            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "Big",
                tint = Color.Gray,
                modifier = Modifier
                    .size(48.dp)
                    .weight(0.1f)
            )
        }

        Spacer(Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "${min.toInt()} kg",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )
            Text(
                text = String.format("%.2f kg", value),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "${max.toInt()} kg",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )
        }
    }
}

@Composable
fun IsSmokingButton(isSmoking: Boolean?, onSelected: (Boolean) -> Unit) {
    GenderCard(
        label = "Smoking",
        icon = Icons.Filled.SmokingRooms,
        selected = isSmoking == true,
        onClick = { onSelected(!isSmoking!!) }
    )
}

@Composable
fun IsAlcoholicButton(isAlcoholic: Boolean?, onSelected: (Boolean) -> Unit) {
    GenderCard(
        label = "Drinking",
        icon = Icons.Filled.LocalDrink,
        selected = isAlcoholic == true,
        onClick = { onSelected(!isAlcoholic!!) }
    )
}
