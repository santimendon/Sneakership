package com.smendon.sneakersapp.ui.main

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.smendon.sneakersapp.ui.sneakerdetails.SneakerDetail
import com.smendon.sneakersapp.ui.sneakerslanding.HomeSneakersList

@Composable
fun SneakersMainScreen() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = NavScreen.Home.route) {
        composable(NavScreen.Home.route) {
            HomeSneakersList(
                viewModel = hiltViewModel(),
                selectSneaker = {
                    navController.navigate("${NavScreen.SneakersDetails.route}/$it")
                }
            )
        }
        composable(
            route = NavScreen.SneakersDetails.routeWithArgument,
            arguments = listOf(
                navArgument(NavScreen.SneakersDetails.argument0) { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val sneakerId =
                backStackEntry.arguments?.getInt(NavScreen.SneakersDetails.argument0)
                    ?: return@composable

            SneakerDetail(sneakerId = sneakerId, sneakerDetailViewModel = hiltViewModel()) {
              navController.navigateUp()
            }
        }
    }
}

sealed class NavScreen(val route: String) {

    object Home : NavScreen("Home")

    object SneakersDetails : NavScreen("SneakersDetails") {

        const val routeWithArgument: String = "SneakersDetails/{sneakerId}"

        const val argument0: String = "sneakerId"
    }
}
