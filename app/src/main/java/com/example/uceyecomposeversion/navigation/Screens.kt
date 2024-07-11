package com.example.uceyecomposeversion.navigation

import com.example.uceyecomposeversion.R

sealed class Screens(val screen: String = "", val icon: Int = 0, val name: String = "") {
    data object About : Screens(screen = "about", icon = R.drawable.about_us_icon, name = "about")
    data object BottleScanner :
        Screens(screen = "bottleScanner", icon = R.drawable.bottle_icon, name = "bottle scanner")

    data object QRScanner :
        Screens(screen = "qrScanner", icon = R.drawable.qr_code_icon, name = "QR scanner")

    data object Instructions :
        Screens(screen = "instructions", icon = R.drawable.instructions_icon, name = "instructions")

    data object QRCamera : Screens(screen = "qrCamera", name = "qrCamera")
}