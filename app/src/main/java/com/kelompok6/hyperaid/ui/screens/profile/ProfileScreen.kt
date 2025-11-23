package com.kelompok6.hyperaid.ui.screens.profile

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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.kelompok6.hyperaid.ui.helper.AuthHelper

// Main Profile Screen
@Preview(showBackground = true)
@Composable
fun ProfileScreen(
    navController: NavController? = null,
    profileViewModel: ProfileViewModel? = null
) {
//    val profileData by profileViewModel.profileData.collectAsState()

    val displayName by produceState(initialValue = "Loading...") {
        value = AuthHelper.getDisplayName()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState())
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                // This box represents the profile avatar
                Box(modifier = Modifier.size(100.dp)) {
                    Box(
                        modifier = Modifier
                            .size(100.dp)
                            .border(3.dp, Color(0xFFD64545), CircleShape)
                            .padding(8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        // You can load image from URL here using Coil or Glide
                        // AsyncImage(model = avatarUrl, contentDescription = "Profile")
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "Profile",
                            tint = Color(0xFFD64545),
                            modifier = Modifier.size(60.dp)
                        )
                    }

                    // This box represents the edit button on the profile avatar
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .align(Alignment.BottomEnd)
                            .background(Color(0xFFD64545), CircleShape)
                            .border(2.dp, Color.White, CircleShape)
                            .clickable { /** onEditClick() **/ },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Clear,
                            contentDescription = "Edit",
                            tint = Color.White,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = displayName,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF2D2D2D)
                )

                Text(
                    text = AuthHelper.getCurrentUser()?.email ?: "Not logged in",
                    fontSize = 14.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }

//        if (statusItems.isNotEmpty()) {
//            StatusCardsRow(statusItems = statusItems)
//        }
//        /** TEST DEFAULT USER STATUS **/
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            UserStatus("Smoking")
            UserStatus("Alcohol")
        }


//        if (personalInfoItems.isNotEmpty()) {
//            ProfileSettingCard("Personal Information") {
//                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
//                    SettingCardContent()
//                }
//            }
//        }

        // ADJUST ALL THE ICON
        ProfileSettingCard("Account Settings") {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                SettingCardContent(
                    {},
                    Icons.Default.Lock,
                    "changePasswordIcon",
                    "Change Password",
                    "Change your password"
                )
                SettingCardContent(
                    {},
                    Icons.Default.Delete,
                    "deleteAccountIcon",
                    "Delete Account",
                    "Permanently delete your account"
                )
            }
        }
        Spacer(modifier = Modifier.padding(8.dp))

        ProfileSettingCard("App Preferences") {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                SettingCardContent(
                    {},
                    Icons.Default.Clear,
                    "globeIcon",
                    "Language",
                    "Change your language"
                )
                SettingCardContent(
                    {},
                    Icons.Default.Notifications,
                    "notificationIcon",
                    "Notifications",
                    "Turn on/off notifications"
                )
            }
        }
        Spacer(modifier = Modifier.padding(8.dp))

        ProfileSettingCard("Health Information") {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                SettingCardContent(
                    {},
                    Icons.Default.Clear,
                    "heartbeatIcon",
                    "Connected Device",
                    "View and manage connected health devices"
                )
                SettingCardContent(
                    {},
                    Icons.Default.Clear,
                    "historyIcon",
                    "Medical History",
                    "View your blood pressure history"
                )
            }
        }
        Spacer(modifier = Modifier.padding(8.dp))

        ProfileSettingCard("Support") {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                SettingCardContent(
                    {},
                    Icons.Default.Clear,
                    "helpIcon",
                    "FAQ",
                    "View frequently asked questions"
                )
                SettingCardContent(
                    {},
                    Icons.Default.Clear,
                    "privacyIcon",
                    "Privacy Policy",
                    "Read our privacy policy"
                )
                SettingCardContent(
                    {},
                    Icons.Default.Clear,
                    "termsIcon",
                    "Terms of Service",
                    "Read our terms and conditions"
                )
            }
        }
        Spacer(modifier = Modifier.padding(8.dp))

        // LOGOUT BUTTON
        ProfileSettingCard("Logout") {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { /** onClick() **/ },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Clear,
                        /** CHANGE THIS TO A RELEVANT ICON **/
                        contentDescription = "Logout",
                        tint = Color.Red,
                        modifier = Modifier.size(24.dp)
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "LOGOUT",
                        fontSize = 14.sp,
                        color = Color.Red,
                        fontWeight = FontWeight.Black
                    )
                }
            }
        }
        Spacer(modifier = Modifier.padding(8.dp))
    }
}

// Setting Card Component
@Composable
fun ProfileSettingCard(
    category: String = "Settings",
    cardContent: @Composable () -> Unit
) {
    Text(
        text = category,
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
        color = Color(0xFF2D2D2D),
        modifier = Modifier.padding(start = 16.dp, bottom = 8.dp)
    )
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        // Card Content
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            cardContent()
        }
    }
}

// Card Content Component
@Composable
fun SettingCardContent(
    onClickAction: () -> Unit,
    icon: ImageVector,
    iconContentDescription: String,
    title: String,
    description: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { /** onClick() **/ },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(40.dp),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                /** CHANGE THIS TO A RELEVANT ICON **/
                contentDescription = iconContentDescription,
                tint = Color.LightGray,
                modifier = Modifier.size(24.dp)
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                fontSize = 14.sp,
                color = Color(0xFF2D2D2D),
                fontWeight = FontWeight.Medium
            )
            Text(
                text = description,
                fontSize = 12.sp,
                color = Color.Gray,
                modifier = Modifier.padding(top = 2.dp)
            )
        }
    }
}

//@Preview(showBackground = true)
@Composable
fun UserStatus(categoryStatus: String, statusValue: String? = "No Information") {
    Card(
        modifier = Modifier.size(180.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Red)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = categoryStatus,
                color = Color.White,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )

            Icon(
                imageVector = if (categoryStatus == "Smoking") Icons.Default.Clear else Icons.Default.Check, // CHANGE THIS TO A RELEVANT ICON
                contentDescription = "statusIcon",
                tint = Color.White,
                modifier = Modifier
                    .size(32.dp)
                    .padding(vertical = 8.dp)
            )

            Text(
                text = statusValue ?: "No Information",
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        }
    }
}
