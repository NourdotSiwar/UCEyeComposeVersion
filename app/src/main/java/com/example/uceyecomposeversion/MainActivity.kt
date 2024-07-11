package com.example.uceyecomposeversion

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.uceyecomposeversion.ui.screens.main.MainScreen
import com.example.uceyecomposeversion.ui.theme.UCEyeComposeVersionTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            UCEyeComposeVersionTheme {
                MainScreen()
            }
        }
    }
}