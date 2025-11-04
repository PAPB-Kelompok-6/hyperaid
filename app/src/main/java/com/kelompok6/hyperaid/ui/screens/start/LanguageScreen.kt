package com.kelompok6.hyperaid.ui.screens.start

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.kelompok6.hyperaid.ui.navigation.Routes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.runtime.*


data class Language(val label: String, val code: String)

private val languageItems = listOf(
    Language("English", "en"),
    Language("Deutsch", "de"),
    Language("한국어", "ko"),
    Language("中文", "zh"),
    Language("Suomi", "fi"),
    Language("Dansk", "da"),
    Language("日本語", "ja"),
    Language("Русский", "ru"),
    Language("العربية", "ar"),
    Language("ไทย", "th"),
    Language("हिन्दी", "hi"),
    Language("বাংলা", "bn"),
    Language("Español", "es"),
    Language("Português", "pt")
)

@Composable
fun LanguageScreen(
    navController: NavHostController? = null,
    onContinue: () -> Unit
) {
    var languagePreference by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(Modifier.height(100.dp))

            Text(
                text = "Setting Language",
                style = MaterialTheme.typography.headlineSmall,
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(8.dp))

            Text(
                text = "Please setting your language before start using this application",
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(horizontal = 24.dp),
            )

            Spacer(Modifier.height(32.dp))

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier
                    //.weight(1f)                 // <- ini biar FAB tidak terdorong
                    .fillMaxWidth(),
                contentPadding = PaddingValues(bottom = 10.dp)  // ruang scroll
            ) {

                items(languageItems) { lang ->
                    val selected = lang.code == languagePreference

                    Card(
                        shape = MaterialTheme.shapes.medium,
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                        modifier = Modifier
                            .height(55.dp)
                            .fillMaxWidth()
                            .clickable { languagePreference = lang.code }
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = lang.label,
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Medium
                            )

                            RadioButton(
                                selected = selected,
                                onClick = { languagePreference = lang.code }
                            )
                        }
                    }
                }
            }
        }
        FloatingActionButton(
            onClick = {
                if (navController != null) {
                    // navigate to AboutScreen when Continue is pressed
                    navController.navigate(Routes.ABOUT) {
                        popUpTo(Routes.LANGUAGE) { inclusive = true }
                        launchSingleTop = true
                    }
                } else {
                    onContinue()
                }
            },
            containerColor = Color(0xFF222222),
            contentColor = Color.White,
            shape = androidx.compose.foundation.shape.CircleShape,
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


@Preview(showBackground = true)
@Composable
private fun PreviewLanguage() {
    LanguageScreen(onContinue = {})
}
