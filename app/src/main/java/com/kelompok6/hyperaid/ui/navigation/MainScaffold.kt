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
import androidx.compose.foundation.layout.size
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
import com.kelompok6.hyperaid.ui.screens.fitsync.bmi.BMIHistoryScreen
import com.kelompok6.hyperaid.ui.screens.fitsync.bmi.BMIScreen
import com.kelompok6.hyperaid.ui.screens.home.HomeScreen
import com.kelompok6.hyperaid.ui.screens.profile.ProfileScreen
import com.kelompok6.hyperaid.ui.screens.reminder.ReminderScreen
import com.kelompok6.hyperaid.ui.screens.vitalsync.VitalsyncDetailHistoryScreen
import com.kelompok6.hyperaid.ui.screens.vitalsync.VitalsyncScreen
import com.kelompok6.hyperaid.ui.screens.vitalsync.SfigmomanometerScreen
import com.kelompok6.hyperaid.ui.screens.fitsync.nutritrack.NutriTrackScreen
import com.kelompok6.hyperaid.ui.screens.fitsync.nutritrack.NutritrackAddScreen
import com.kelompok6.hyperaid.ui.screens.fitsync.nutritrack.NutritrackResultsLoadingScreen
import com.kelompok6.hyperaid.ui.screens.home.NotificationScreen
import com.kelompok6.hyperaid.ui.screens.vitalsync.VitalsyncAddNotesScreen
import com.kelompok6.hyperaid.ui.screens.measure.MeasureInstruction
import com.kelompok6.hyperaid.ui.screens.measure.MeasureProcess
import com.kelompok6.hyperaid.ui.screens.measure.MeasureResult

@Composable
fun MainScaffold(
    onLogout: () -> Unit
) {
    val navController = rememberNavController()

    // observe current route so we can conditionally show bottom bar
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    // routes where bottom bar should be hidden
    val hideBottomBarRoutes = listOf(
        Routes.MEASURE_INSTRUCTION,
        Routes.MEASURE_PROCESS,
        Routes.MEASURE_RESULT,
        Routes.VITALSYNC_SFIGMOMANOMETER, // hide nav bar on sfigmomanometer measurement screen
        Routes.VITALSYNC_ADDNOTES, // hide nav bar on add notes screen
        Routes.NOTIFICATION // hide nav bar on notification screen
    )

    Scaffold(
        bottomBar = {
            if (currentRoute == null || !hideBottomBarRoutes.contains(currentRoute)) {
                MainBottomBar(navController)
            }
        }
    ) { innerPadding ->
        MainNavHost(
            navController,
            Modifier.padding(innerPadding),
            onLogout = onLogout
        )
    }
}

@Composable
private fun MainNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    onLogout: () -> Unit
) {

    NavHost(navController, startDestination = Routes.HOME, modifier = modifier) {
        composable(Routes.HOME) { HomeScreen(navController) }
        composable(Routes.FITSYNC) { BMIScreen(navController) }
        composable(Routes.FITSYNC_HISTORY) { BMIHistoryScreen(navController) }
        composable(Routes.VITALSYNC) { VitalsyncScreen(navController) }
        composable(Routes.VITALSYNC_HISTORY) { VitalsyncDetailHistoryScreen() }
        composable(Routes.VITALSYNC_ADDNOTES) { VitalsyncAddNotesScreen(navController) }
        // Added Sfigmomanometer route here so VitalsyncScreen's NavController can navigate to it
        composable(Routes.VITALSYNC_SFIGMOMANOMETER) { SfigmomanometerScreen(navController) }
        composable(Routes.REMINDER) { ReminderScreen(navController) }
        composable(Routes.PROFILE) {
            ProfileScreen(
                navController = navController,
                onLogout = onLogout
                /** profileViewModel **/
            ) // NANTI DI-PASS PROFILE VIEW MODEL
        }
        composable(Routes.NUTRITRACK) { NutriTrackScreen(navController) }
        composable(Routes.MEASURE_INSTRUCTION) { MeasureInstruction(navController) }
        composable(Routes.MEASURE_PROCESS) { MeasureProcess(navController) }
        composable(Routes.MEASURE_RESULT) { MeasureResult(navController) }
        composable(Routes.NOTIFICATION) { NotificationScreen(navController) }
        composable(Routes.NUTRITRACK_ADD) { NutritrackAddScreen(navController) }
        composable(Routes.NUTRITRACK_LOADING) { NutritrackResultsLoadingScreen() }

    }
}

@Composable
fun MainBottomBar(navController: NavHostController, modifier: Modifier = Modifier) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 0.dp, vertical = 0.dp),
        shape = RoundedCornerShape(
            topStart = 24.dp,
            topEnd = 24.dp,
            bottomStart = 0.dp,
            bottomEnd = 0.dp
        ),
        color = Color(0xFFF2F2F2),
        tonalElevation = 4.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
                .padding(
                    start = 12.dp,
                    top = 15.dp,
                    end = 12.dp,
                    bottom = 15.dp
                ),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            bottomItems.forEach { item ->
                // Treat NutriTrack as part of FitSync for bottom nav highlighting
                val isSelected = currentRoute == item.route ||
                        (item.route == Routes.FITSYNC && currentRoute == Routes.NUTRITRACK)

                val scale by animateFloatAsState(
                    targetValue = if (isSelected) 1f else 1f,
                    animationSpec = tween(durationMillis = 220)
                )

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
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
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
                        modifier = Modifier
                            .size(if (isSelected) 24.dp else 24.dp) // <-- increased sizes
                            .scale(scale)
                            .padding(
                                top = 5.dp
                            )
                    )
                    Text(
                        text = item.label,
                        style = MaterialTheme.typography.labelSmall,
                        color = labelColor,
                        modifier = Modifier.padding(top = 4.dp) // slightly larger gap
                    )
                }
            }
        }
    }
}