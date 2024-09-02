package com.example.uceyecomposeversion.ui.screens.info

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.KeyboardArrowRight
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.uceyecomposeversion.R
import com.example.uceyecomposeversion.viewmodels.QrViewModel
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import java.util.Date

@Composable
fun InfoScreen(navController: NavController) {
    val context = LocalContext.current
    val qrViewModel: QrViewModel = viewModel()
    val medicines by qrViewModel.medicines.collectAsState()

    Scaffold(
        topBar = {
            InstructionsTopAppBar(
                navController = navController, context = context
            )
        },
    ) { innerPadding ->
        if (medicines.isEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = "No Medicine List Added"
                )
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(innerPadding)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                medicines.forEach { medicine ->

                    ListItem(modifier = Modifier.clickable {
                        val encodedMedicineName = URLEncoder.encode(medicine.medicineName, StandardCharsets.UTF_8.toString())
                        navController.navigate(context.getString(R.string.medicine_detail_screen) + "/$encodedMedicineName")
                    },
                        headlineContent = { Text(text = medicine.medicineName) },
                        trailingContent = {
                            Column {
                                Text(
                                    text = qrViewModel.getRelativeTime(Date(medicine.timestamp))
                                )
                                Spacer(Modifier.height(10.dp))
                                Icon(
                                    imageVector = Icons.Outlined.KeyboardArrowRight,
                                    contentDescription = null,
                                    modifier = Modifier.align(Alignment.End)
                                )
                            }
                        },
                        leadingContent = {
                            Box(
                                modifier = Modifier.padding(vertical = 10.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(40.dp)
                                        .background(
                                            MaterialTheme.colorScheme.surfaceTint,
                                            shape = CircleShape
                                        ), contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = medicine.medicineName.first().toString(),
                                        color = MaterialTheme.colorScheme.onPrimary,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 20.sp
                                    )
                                }
                            }
                        })
                }
            }
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun InstructionsTopAppBar(
    navController: NavController,
    context: Context,
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
            IconButton(
                onClick = {
                    navController.navigate(context.getString(R.string.about_us_screen))
                },
            ) {
                Icon(
                    imageVector = Icons.Outlined.Info,
                    tint = MaterialTheme.colorScheme.onPrimary,
                    contentDescription = null
                )
            }
            IconButton(
                onClick = {
                    //TODO:
                },
            ) {
                Icon(
                    imageVector = Icons.Outlined.MoreVert,
                    tint = MaterialTheme.colorScheme.onPrimary,
                    contentDescription = null
                )
            }
        },
    )
}