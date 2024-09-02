package com.example.uceyecomposeversion.ui.screens.main

import android.Manifest
import android.content.Context
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
    val requestPermissionLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            isCameraPermissionGranted = isGranted
        }
    val bottomNavItems = listOf(
        BottomNavigationItem(
            stringResource(id = R.string.bottle_scanner_screen), R.drawable.bottle_icon
        ), BottomNavigationItem(
            stringResource(id = R.string.qr_scanner_screen), R.drawable.qr_code_icon
        ), BottomNavigationItem(
            stringResource(id = R.string.information_screen), R.drawable.instructions_icon
        )
    )

    LaunchedEffect(Unit) {
        requestPermissionLauncher.launch(Manifest.permission.CAMERA)
    }

    Scaffold(topBar = {
        MainTopAppBar(navController = navController, context = context, isCameraPermissionGranted)
    }, bottomBar = {
        MainBottomAppBar(bottomNavItems, navController, isCameraPermissionGranted)

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
    navController: NavHostController, context: Context, isCameraPermissionGranted: Boolean
) {
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route
    val showBackButtonRoutes = setOf(stringResource(R.string.about_us_screen))
    val showBackButton = currentRoute in showBackButtonRoutes
    val showInfoBackRoutes = setOf(
        stringResource(R.string.bottle_scanner_screen),
        stringResource(R.string.qr_scanner_screen),
        stringResource(R.string.information_screen),
        )
    val showInfoButton = currentRoute in showInfoBackRoutes

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
        navigationIcon = {
            if (showBackButton) {
                IconButton(onClick = {
                    navController.popBackStack()
                }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        },
        actions = {
            if (showInfoButton) {
                IconButton(
                    onClick = {
                        navController.navigate(context.getString(R.string.about_us_screen))
                    },
                    enabled = isCameraPermissionGranted,
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Info,
                        tint = MaterialTheme.colorScheme.onPrimary,
                        contentDescription = null
                    )
                }
            }
        },
    )
}

@Composable
private fun MainBottomAppBar(
    bottomNavItems: List<BottomNavigationItem>,
    navController: NavHostController,
    isCameraPermissionGranted: Boolean
) {
    val currentDestination = navController.currentBackStackEntryAsState().value?.destination

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