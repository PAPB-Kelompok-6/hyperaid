package com.kelompok6.hyperaid.ui.navigation
//
//import androidx.compose.foundation.layout.padding
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.Surface
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.LaunchedEffect
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import androidx.navigation.compose.rememberNavController
//
//@Preview(showBackground = true)
//@Composable
//fun MainBottomBarPreview() {
//    val navController = rememberNavController()
//    MaterialTheme {
//        Surface {
//            MainBottomBar(navController, modifier = Modifier.padding(8.dp))
//        }
//    }
//}
//
//@Preview(showBackground = true, name = "BottomBar - Home selected")
//@Composable
//fun MainBottomBarPreview_Home() {
//    val navController = rememberNavController()
//    LaunchedEffect(Unit) {
//        navController.navigate(Routes.HOME) {
//            popUpTo(navController.graph.startDestinationId)
//            launchSingleTop = true
//        }
//    }
//
//    MaterialTheme {
//        Surface {
//            MainBottomBar(navController, modifier = Modifier.padding(8.dp))
//        }
//    }
//}
//
//@Preview(showBackground = true, name = "BottomBar - Profile selected")
//@Composable
//fun MainBottomBarPreview_Profile() {
//    val navController = rememberNavController()
//    LaunchedEffect(Unit) {
//        navController.navigate(Routes.PROFILE) {
//            popUpTo(navController.graph.startDestinationId)
//            launchSingleTop = true
//        }
//    }
//
//    MaterialTheme {
//        Surface {
//            MainBottomBar(navController, modifier = Modifier.padding(8.dp))
//        }
//    }
//}
