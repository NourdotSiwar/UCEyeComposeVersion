package com.example.uceyecomposeversion.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountBox
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.List
import com.example.uceyewithcompose.R

sealed class Screens(val screen: String, val icon: Int, val name: String) {
    data object About : Screens(screen = "about", icon = R.drawable.about_us_icon, name = "about")
    data object BottleScanner : Screens(screen = "bottleScanner", icon = R.drawable.bottle_icon, name = "bottle scanner")
    data object QRScanner : Screens(screen = "qrScanner", icon = R.drawable.qr_code_icon, name = "QR scanner")
    data object Instructions : Screens(screen = "instructions", icon = R.drawable.instructions_icon, name = "instructions")
}