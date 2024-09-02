package com.example.uceyecomposeversion.navigation

import android.content.Context
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.uceyecomposeversion.R
import com.example.uceyecomposeversion.ui.screens.about.AboutScreen
import com.example.uceyecomposeversion.ui.screens.bottleScanner.BottleScannerScreen
import com.example.uceyecomposeversion.ui.screens.info.InfoScreen
import com.example.uceyecomposeversion.ui.screens.medicineDetail.MedicineDetailScreen
import com.example.uceyecomposeversion.ui.screens.qrScanner.QRScannerScreen

@Composable
fun Navigation(navController: NavHostController, context: Context) {
    NavHost(
        navController = navController,
        startDestination = context.getString(R.string.qr_scanner_screen)
    ) {
        composable(context.getString(R.string.about_us_screen),
            enterTransition = {
            fadeIn(
//                    animationSpec = tween(
//                        200, easing = LinearEasing
//                    )
            ) + slideIntoContainer(
                animationSpec = tween(200, easing = EaseIn),
                towards = AnimatedContentTransitionScope.SlideDirection.Start
            )
        },
            exitTransition = {
            fadeOut(
//                    animationSpec = tween(
//                        300, easing = LinearEasing
//                    )
            ) + slideOutOfContainer(
                animationSpec = tween(200, easing = EaseOut),
                towards = AnimatedContentTransitionScope.SlideDirection.End
            )
        }
        ) {
            AboutScreen(navController)
        }
        composable(context.getString(R.string.bottle_scanner_screen)) {
            BottleScannerScreen(navController)
        }
        composable(context.getString(R.string.qr_scanner_screen)) {
            QRScannerScreen(navController)
        }
        composable(context.getString(R.string.information_screen)) {
            InfoScreen(navController)
        }
        composable(
            enterTransition = {
                fadeIn(
//                    animationSpec = tween(
//                        200, easing = LinearEasing
//                    )
                ) + slideIntoContainer(
                    animationSpec = tween(200, easing = EaseIn),
                    towards = AnimatedContentTransitionScope.SlideDirection.Start
                )
            },
            exitTransition = {
                fadeOut(
//                    animationSpec = tween(
//                        300, easing = LinearEasing
//                    )
                ) + slideOutOfContainer(
                    animationSpec = tween(200, easing = EaseOut),
                    towards = AnimatedContentTransitionScope.SlideDirection.End
                )
            },
            route = context.getString(R.string.medicine_detail_screen) +
                    "/{medicineName}",
            arguments = listOf(navArgument("medicineName")
            { type = NavType.StringType })){ backStackEntry ->
            val medicineDetailNavEntry = backStackEntry.arguments?.getString("medicineName")
                MedicineDetailScreen(navController, medicineName = medicineDetailNavEntry!!)
        }
    }
}
