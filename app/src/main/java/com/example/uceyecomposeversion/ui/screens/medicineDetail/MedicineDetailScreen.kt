package com.example.uceyecomposeversion.ui.screens.medicineDetail

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.uceyecomposeversion.R
import com.example.uceyecomposeversion.model.room.MedicineEntity
import com.example.uceyecomposeversion.viewmodels.medicineViewModel
import kotlinx.coroutines.launch
import java.net.URLDecoder
import java.nio.charset.StandardCharsets

@Composable
fun MedicineDetailScreen(navController: NavController, medicineName: String) {
    val context = LocalContext.current
    val viewModel: medicineViewModel = viewModel()
    val decodedMedicineName = URLDecoder.decode(medicineName, StandardCharsets.UTF_8.toString())
    val medicine by viewModel.getMedicineByName(decodedMedicineName).collectAsState(initial = null)

    Scaffold(topBar = {
        MedicineDetailTopAppBar(
            navController = navController,
            context = context,
            viewModel = viewModel,
            medicine = medicine
        )
    }) { innerPadding ->
        medicine?.let { medicineObject ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    medicineObject.medicineName
                )
                Text(
                    medicineObject.frequency
                )
            }
        }
    }

}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun MedicineDetailTopAppBar(
    navController: NavController,
    context: Context,
    viewModel: medicineViewModel,
    medicine: MedicineEntity?
) {
    var showDialog by remember { mutableStateOf(false) }
    var message by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()

    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary
        ),
        title = {},
        navigationIcon = {
            IconButton(onClick = {
                navController.popBackStack()
            }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        },
        actions = {
            IconButton(
                onClick = {
                    showDialog = true
                    message = context.getString(R.string.delete_one_medicine_dialog)
                },
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    tint = MaterialTheme.colorScheme.onPrimary,
                    contentDescription = null
                )
            }
            IconButton(
                onClick = {
                    showDialog = true
                    message = context.getString(R.string.edit_one_medicine_dialog)
                },
            ) {
                Icon(
                    imageVector = Icons.Outlined.Edit,
                    tint = MaterialTheme.colorScheme.onPrimary,
                    contentDescription = null
                )
            }

            if (showDialog) {
                AlertDialog(containerColor = MaterialTheme.colorScheme.primaryContainer,
                    icon = {
                        Icon(
                            imageVector = Icons.Outlined.Warning,
                            contentDescription = "Warning Icon",
                            tint = MaterialTheme.colorScheme.error
                        )
                    }, text = {
                        Text(
                            text = message,
                            color = MaterialTheme.colorScheme.surfaceTint
                        )
                    }, onDismissRequest = { showDialog = false }, confirmButton = {
                        TextButton(onClick = {
                            if(message == context.getString(R.string.delete_one_medicine_dialog)){
                                scope.launch {
                                    viewModel.deleteMedicine(medicine?.medicineName ?: "")
                                    navController.navigate(context.getString(R.string.information_screen))
                                }
                            } else if(message == context.getString(R.string.edit_one_medicine_dialog)){
                                //TODO: ADD EDIT SCREEN
                            }
                        }) {
                            Text(
                                text = "Confirm", color = MaterialTheme.colorScheme.surfaceTint
                            )
                        }
                    }, dismissButton = {
                        TextButton(onClick = { showDialog = false }) {
                            Text(
                                text = "Dismiss", color = MaterialTheme.colorScheme.surfaceTint
                            )
                        }
                    })
            }
        },


    )
}