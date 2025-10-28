package com.kelompok6.hyperaid.ui.navigation

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material3.Scaffold
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.kelompok6.hyperaid.ui.screens.fitsync.bmi.BMIScreen
import com.kelompok6.hyperaid.ui.screens.home.HomeScreen
import com.kelompok6.hyperaid.ui.screens.profile.ProfileScreen
import com.kelompok6.hyperaid.ui.screens.reminder.ReminderScreen
import com.kelompok6.hyperaid.ui.screens.vitalsync.VitalsyncScreen

@Composable
fun MainScaffold() {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = { MainBottomBar(navController) }
    ) { innerPadding ->
        MainNavHost(navController, Modifier.padding(innerPadding))
    }
}

@Composable
private fun MainNavHost(navController: NavHostController, modifier: Modifier = Modifier) {
    // Start at HOME, and define the top-level destinations in the order you wanted.
    NavHost(navController, startDestination = Routes.HOME, modifier = modifier) {
        composable(Routes.HOME) { HomeScreen(navController) }
        composable(Routes.FITSYNC) { BMIScreen(navController) }
        composable(Routes.VITALSYNC) { VitalsyncScreen(navController) }
        composable(Routes.REMINDER) { ReminderScreen(navController) }
        composable(Routes.PROFILE) { ProfileScreen(navController) }
    }
}

@Composable
fun MainBottomBar(navController: NavHostController, modifier: Modifier = Modifier) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    // Rounded container to match mockup (only top corners rounded)
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 0.dp, vertical = 0.dp),
        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp, bottomStart = 0.dp, bottomEnd = 0.dp),
        color = MaterialTheme.colorScheme.surface,
        tonalElevation = 4.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding( // use the 4-edges overload
                    start = 12.dp,
                    top = 12.dp,
                    end = 12.dp,
                    bottom = 30.dp
                ),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            bottomItems.forEach { item ->
                val isSelected = currentRoute == item.route

                // animated scale for icon
                val scale by animateFloatAsState(
                    targetValue = if (isSelected) 1.18f else 1f,
                    animationSpec = tween(durationMillis = 220)
                )

                // animated color for label
                val labelColor by animateColorAsState(
                    targetValue = if (isSelected) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.onSurfaceVariant,
                    animationSpec = tween(durationMillis = 220)
                )

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .weight(1f)
                        .clickable {
                            if (!isSelected) {
                                navController.navigate(item.route) {
                                    popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        }
                        .animateContentSize()
                ) {
                    Icon(
                        painter = painterResource(id = if (isSelected) item.activeIconRes else item.inactiveIconRes),
                        contentDescription = item.label,
                        tint = Color.Unspecified,
                        modifier = Modifier.scale(scale)
                    )
                    Text(
                        text = item.label,
                        style = MaterialTheme.typography.labelSmall,
                        color = labelColor,
                        modifier = Modifier.padding(top = 6.dp) // slightly larger gap
                    )
                }
            }
        }
    }
}