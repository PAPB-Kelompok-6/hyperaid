package com.kelompok6.hyperaid.ui.navigation

import com.kelompok6.hyperaid.R

sealed class BottomNavItem(
    val route: String,
    val activeIconRes: Int,
    val inactiveIconRes: Int,
    val label: String
) {
    object Home : BottomNavItem(Routes.HOME, R.drawable.navbar_home_active, R.drawable.navbar_home_inactive, "Home")
    object FitSync : BottomNavItem(Routes.FITSYNC, R.drawable.navbar_fit_sync_active, R.drawable.navbar_fit_sync_inactive, "FitSync")
    object VitalSync : BottomNavItem(Routes.VITALSYNC, R.drawable.navbar_vital_sync_active, R.drawable.navbar_vital_sync_inactive, "VitalSync")
    object Reminder : BottomNavItem(Routes.REMINDER, R.drawable.navbar_reminder_active, R.drawable.navbar_reminder_inactive, "Reminder")
    object Profile : BottomNavItem(Routes.PROFILE, R.drawable.navbar_profile_active, R.drawable.navbar_profile_inactive, "Profile")
}

val bottomItems = listOf(
    BottomNavItem.Home,
    BottomNavItem.FitSync,
    BottomNavItem.VitalSync,
    BottomNavItem.Reminder,
    BottomNavItem.Profile
)