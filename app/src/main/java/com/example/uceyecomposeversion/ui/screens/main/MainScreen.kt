package com.example.uceyecomposeversion.ui.screens.main

import android.Manifest
import android.content.Context
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.uceyecomposeversion.R
import com.example.uceyecomposeversion.navigation.Navigation

@Composable
fun MainScreen() {
    val context = LocalContext.current
    val navController = rememberNavController()
    var isCameraPermissionGranted by rememberSaveable { mutableStateOf(false) }
    val requestPermissionLauncher = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            isCameraPermissionGranted = isGranted
        }
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route
    val currentDestination = navController.currentBackStackEntryAsState().value?.destination
    val bottomNavItems = listOf(
        BottomNavigationItem(
            stringResource(id = R.string.bottle_scanner_screen), R.drawable.bottle_icon
        ), BottomNavigationItem(
            stringResource(id = R.string.qr_scanner_screen), R.drawable.qr_code_icon
        ), BottomNavigationItem(
            stringResource(id = R.string.information_screen), R.drawable.instructions_icon
        )
    )
    val showBottomNavigationBarRoutes = setOf(
        stringResource(R.string.bottle_scanner_screen),
        stringResource(R.string.qr_scanner_screen),
        stringResource(R.string.information_screen),
    )
    val showBottomNavigationBar = currentRoute in showBottomNavigationBarRoutes

    LaunchedEffect(Unit) {
        requestPermissionLauncher.launch(Manifest.permission.CAMERA)
    }

    Scaffold(bottomBar = {
        if(showBottomNavigationBar) {
            MainBottomAppBar(
                bottomNavItems = bottomNavItems,
                navController = navController,
                isCameraPermissionGranted = isCameraPermissionGranted,
                currentDestination = currentDestination
            )
        }
    }) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            if (isCameraPermissionGranted) {
                Navigation(
                    navController = navController,
                    context = context
                )
            } else {
                PermissionRequestScreen()
            }
        }
    }
}

@Composable
private fun MainBottomAppBar(
    bottomNavItems: List<BottomNavigationItem>,
    navController: NavHostController,
    isCameraPermissionGranted: Boolean,
    currentDestination: NavDestination?
) {

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onPrimary
    ) {
        bottomNavItems.forEach { item ->
            NavigationBarItem(selected = currentDestination?.route == item.title, onClick = {
                navController.navigate(item.title) {
                    launchSingleTop = true
                }
            }, colors = NavigationBarItemDefaults.colors(
                selectedTextColor = MaterialTheme.colorScheme.onPrimary,
                indicatorColor = MaterialTheme.colorScheme.onSurface
            ), label = {
                Text(text = item.title, color = MaterialTheme.colorScheme.onPrimary)
            }, enabled = isCameraPermissionGranted, alwaysShowLabel = false, icon = {
                Icon(
                    painter = painterResource(item.icon),
                    contentDescription = item.title,
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            })

        }
    }
}

data class BottomNavigationItem(
    val title: String,
    val icon: Int,
)