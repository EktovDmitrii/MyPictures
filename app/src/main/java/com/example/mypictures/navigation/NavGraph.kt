package com.example.mypictures.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.mypictures.presentation.PhotoListScreen
import com.example.mypictures.presentation.ZoomableImageScreen

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screens.PhotoList.route
    )
    {
        composable(route = Screens.PhotoList.route) {
            PhotoListScreen(navController)
        }
        composable(route = Screens.PhotoDetail.route,
            arguments = listOf(
                navArgument("photoUrl") {
                    type = NavType.StringType
                }
            )
        ) {
            ZoomableImageScreen(
                navController
            )
        }
    }
}