package com.example.uceyecomposeversion.ui.screens.main

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.rememberNavController
import com.example.uceyecomposeversion.R
import com.example.uceyecomposeversion.navigation.BottomNavItem
import com.example.uceyecomposeversion.navigation.Navigation
import com.example.uceyecomposeversion.ui.components.BottomBar

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val context = LocalContext.current
    var isCameraPermissionGranted by rememberSaveable { mutableStateOf(false) }
    val requestPermissionLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            isCameraPermissionGranted = isGranted
        }

    LaunchedEffect(Unit) {
        requestPermissionLauncher.launch(Manifest.permission.CAMERA)
    }

    val bottomNavItems = listOf(
        BottomNavItem(context.getString(R.string.about_us_screen), R.drawable.about_us_icon),
        BottomNavItem(context.getString(R.string.bottle_scanner_screen), R.drawable.bottle_icon),
        BottomNavItem(context.getString(R.string.qr_scanner_screen), R.drawable.qr_code_icon),
        BottomNavItem(context.getString(R.string.information_screen), R.drawable.instructions_icon),
    )

    Scaffold(bottomBar = {
        BottomBar(
            navController = navController,
            items = bottomNavItems,
            iconsEnabled = isCameraPermissionGranted
        )
    }) { innerPadding ->

        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            if (isCameraPermissionGranted) {
                Navigation(
                    navController = navController, paddingValues = innerPadding, context = context
                )
            } else {
                PermissionRequestScreen {
                    requestPermissionLauncher.launch(Manifest.permission.CAMERA)
                }
            }
        }
    }
}
