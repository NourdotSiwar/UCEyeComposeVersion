package com.example.uceyecomposeversion.ui.screens.main

import android.Manifest
import android.content.Context
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.uceyecomposeversion.R
import com.example.uceyecomposeversion.navigation.BottomNavItem
import com.example.uceyecomposeversion.navigation.Navigation

@Composable
fun MainScreen() {
    val context = LocalContext.current
    val navController = rememberNavController()
    var isCameraPermissionGranted by rememberSaveable { mutableStateOf(false) }
    val requestPermissionLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            isCameraPermissionGranted = isGranted
        }
    val bottomNavItems = listOf(
        BottomNavItem(stringResource(id = R.string.qr_scanner_screen), R.drawable.qr_code_icon),
        BottomNavItem(stringResource(id = R.string.bottle_scanner_screen), R.drawable.bottle_icon),
        BottomNavItem(stringResource(id = R.string.information_screen), R.drawable.instructions_icon)
    )

    LaunchedEffect(Unit) {
        requestPermissionLauncher.launch(Manifest.permission.CAMERA)
    }

    Scaffold(topBar = {
        MainTopAppBar(
            navController = navController, context = context
        )
    }, bottomBar = {
        MainBottomNavigationBar(
            navController = navController,
            items = bottomNavItems,
            currentRoute = navController.currentDestination?.route
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

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun MainTopAppBar(
    navController: NavHostController, context: Context
) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary
        ),
        title = {
            Text(
                stringResource(id = R.string.app_name)
            )
        },
        actions = {
            IconButton(onClick = {
                navController.navigate(context.getString(R.string.about_us_screen))
            }) {
                Icon(
                    imageVector = Icons.Outlined.Info,
                    tint = MaterialTheme.colorScheme.onPrimary,
                    contentDescription = "Localized description"
                )
            }
        },
    )
}

@Composable
fun MainBottomNavigationBar(
    navController: NavController, items: List<BottomNavItem>, currentRoute: String?
) {
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onPrimary
    ) {
        items.forEach { item ->
            NavigationBarItem(icon = {
                Icon(
                    painter = painterResource(id = item.icon),
                    contentDescription = null,
                    tint = if (currentRoute == item.screen) Color.White else Color.Black
                )
            }, selected = currentRoute == item.screen, onClick = {
                navController.navigate(item.screen) {
                    popUpTo(navController.graph.startDestinationId)
                    launchSingleTop = true
                }
            })
        }
    }
}
