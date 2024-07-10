package com.example.uceyecomposeversion.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.uceyecomposeversion.ui.screens.about.AboutScreen
import com.example.uceyecomposeversion.ui.screens.bottleScanner.BottleScannerScreen
import com.example.uceyecomposeversion.ui.screens.info.InfoScreen
import com.example.uceyewithcompose.ui.screens.qrScanner.QRScannerScreen

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun Navigation(navController: NavHostController, paddingValues: PaddingValues) {
    NavHost(
        navController = navController,
        startDestination = Screens.BottleScanner.screen,
        modifier = Modifier.padding(paddingValues)
    ) {
        composable(Screens.About.screen) {
            AboutScreen()
        }
        composable(Screens.BottleScanner.screen) {
            BottleScannerScreen(navController)
        }
        composable(Screens.QRScanner.screen) {
            QRScannerScreen(navController)
        }

        composable(Screens.Instructions.screen) {
            InfoScreen(navController)
        }
    }
}


