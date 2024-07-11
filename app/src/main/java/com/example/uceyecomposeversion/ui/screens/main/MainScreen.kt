package com.example.uceyecomposeversion.ui.screens.main

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.example.uceyecomposeversion.navigation.BottomNavItem
import com.example.uceyecomposeversion.navigation.Navigation
import com.example.uceyecomposeversion.navigation.Screens
import com.example.uceyecomposeversion.ui.components.BottomBar
import com.example.uceyecomposeversion.R

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val bottomNavItems = listOf(
        BottomNavItem(Screens.About.screen, R.drawable.about_us_icon, Screens.About.name ),
        BottomNavItem(Screens.BottleScanner.screen, R.drawable.bottle_icon, Screens.BottleScanner.name ),
        BottomNavItem(Screens.QRScanner.screen, R.drawable.qr_code_icon, Screens.QRScanner.name ),
        BottomNavItem(Screens.Instructions.screen, R.drawable.instructions_icon, Screens.Instructions.name ),
    )

    Scaffold(
    bottomBar = { BottomBar(navController = navController, items = bottomNavItems) }
    ) { innerPadding ->
        Navigation(navController = navController, paddingValues = innerPadding)
    }
}
