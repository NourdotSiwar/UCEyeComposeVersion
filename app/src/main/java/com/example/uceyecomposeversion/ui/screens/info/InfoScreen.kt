package com.example.uceyecomposeversion.ui.screens.info

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.uceyecomposeversion.viewmodels.QrViewModel

@Composable
fun InfoScreen(navController: NavController) {
    val context = LocalContext.current
    val qrViewModel: QrViewModel = viewModel()
    val medicines by qrViewModel.medicines.collectAsState()

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,

            ) {
            if (medicines.isEmpty()) {
                Text(
                    text = "No Medicine List Added"
                )
            } else {
                medicines.forEach { medicine ->
                    Text(
                        text = medicine.medicineName
                    )
                }
            }
        }
    }
}