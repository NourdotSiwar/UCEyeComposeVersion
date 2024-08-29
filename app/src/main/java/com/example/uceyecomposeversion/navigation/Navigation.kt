package com.example.uceyecomposeversion.navigation

import android.content.Context
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.uceyecomposeversion.R
import com.example.uceyecomposeversion.ui.screens.about.AboutScreen
import com.example.uceyecomposeversion.ui.screens.bottleScanner.BottleScannerScreen
import com.example.uceyecomposeversion.ui.screens.info.InfoScreen
import com.example.uceyecomposeversion.ui.screens.qrScanner.QRScannerScreen

@Composable
fun Navigation(navController: NavHostController, paddingValues: PaddingValues, context: Context) {
    NavHost(
        navController = navController,
        startDestination = context.getString(R.string.qr_scanner_screen),
        modifier = Modifier.padding(paddingValues)
    ) {
        composable(context.getString(R.string.about_us_screen)) {
            AboutScreen()
        }
        composable(context.getString(R.string.bottle_scanner_screen)) {
            BottleScannerScreen()
        }
        composable(context.getString(R.string.qr_scanner_screen)) {
            QRScannerScreen(navController)
        }
        composable(context.getString(R.string.information_screen)) {
            InfoScreen()
        }
    }
}


