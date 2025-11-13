package com.kelompok6.hyperaid.ui.navigation

import com.kelompok6.hyperaid.R

sealed class BottomNavItem(
    val route: String,
    val activeIconRes: Int,
    val inactiveIconRes: Int,
    val label: String
) {
    object Home : BottomNavItem(Routes.HOME, R.drawable.navbar_home_on, R.drawable.navbar_home_off, "Home")
    object FitSync : BottomNavItem(Routes.FITSYNCBMI, R.drawable.navbar_fitsync_on, R.drawable.navbar_fitsync_off, "FitSync")
    object VitalSync : BottomNavItem(Routes.VITALSYNC, R.drawable.navbar_vitalsync_on, R.drawable.navbar_vitalsync_off, "VitalSync")
    object Reminder : BottomNavItem(Routes.REMINDER, R.drawable.navbar_reminder_on, R.drawable.navbar_reminder_off, "Reminder")
    object Profile : BottomNavItem(Routes.PROFILE, R.drawable.navbar_profile_on, R.drawable.navbar_profile_off, "Profile")
}

val bottomItems = listOf(
    BottomNavItem.Home,
    BottomNavItem.FitSync,
    BottomNavItem.VitalSync,
    BottomNavItem.Reminder,
    BottomNavItem.Profile
)