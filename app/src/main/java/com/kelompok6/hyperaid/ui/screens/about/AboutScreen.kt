package com.kelompok6.hyperaid.ui.screens.about

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kelompok6.hyperaid.data.enum.Gender
import com.kelompok6.hyperaid.ui.helper.AuthHelper
import com.kelompok6.hyperaid.ui.navigation.Routes
import com.kelompok6.hyperaid.ui.screens.start.OnboardingViewModel

@Composable
fun AboutScreen(
    navController: NavHostController,
    viewModel: OnboardingViewModel = viewModel()
) {
    var checking by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        val uid = AuthHelper.getCurrentUser()?.uid
        if (uid != null) {
            val missing = viewModel.checkIfAboutIsMissing(uid)

            if (!missing) {
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

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp)
    ) {
        // konten utama
        Column(
            modifier = Modifier.align(Alignment.Center),
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
                selectedGender = state.gender,
                onSelected = { g -> viewModel.update { it.copy(gender = g) } }
            )

            HeightSlider()
            WeightSlider()

            Row {
                IsSmokingButton()
                IsAlcoholicButton()
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
                .padding(bottom = 100.dp)
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
    icon: androidx.compose.ui.graphics.vector.ImageVector,
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

@Composable
fun HeightSlider() {

}

@Composable
fun WeightSlider() {

}

@Composable
fun IsSmokingButton() {

}

fun IsAlcoholicButton() {

}

@Preview(showBackground = true)
@Composable
private fun PreviewAbout() {
//    AboutScreen()
}
