package com.example.uceyecomposeversion

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.graphics.Color
import com.example.uceyecomposeversion.ui.screens.main.MainScreen
import com.example.uceyecomposeversion.ui.theme.UCEyeComposeVersionTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            // Remember the SystemUiController
            val systemUiController = rememberSystemUiController()
            val isDarkTheme = isSystemInDarkTheme()

            // Set the status bar and navigation bar colors
            LaunchedEffect(Unit) {
                systemUiController.setSystemBarsColor(
                    color = if (isDarkTheme) Color.Black else Color.White
                )
                systemUiController.setNavigationBarColor(
                    color = if (isDarkTheme) Color.Black else Color.White
                )
            }

            UCEyeComposeVersionTheme {
                MainScreen()
            }
        }
    }
}