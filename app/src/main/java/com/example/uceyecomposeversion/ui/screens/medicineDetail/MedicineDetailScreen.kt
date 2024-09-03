package com.example.uceyecomposeversion.ui.screens.medicineDetail

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.uceyecomposeversion.R
import com.example.uceyecomposeversion.model.room.MedicineEntity
import com.example.uceyecomposeversion.viewmodels.medicineViewModel
import kotlinx.coroutines.launch
import java.net.URLDecoder
import java.nio.charset.StandardCharsets
import java.util.Date

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
                    .fillMaxWidth()
                    .padding(innerPadding)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                MedicineListItem(medicineObject, viewModel)

                MedicineObjectCard(medicineObject, context)
            }
        }
    }
}

@Composable
private fun MedicineListItem(
    medicineObject: MedicineEntity,
    viewModel: medicineViewModel
) {
    ListItem(
        headlineContent = { Text(text = medicineObject.medicineName) },
        trailingContent = {
            Column {
                Text(
                    text = viewModel.getRelativeTime(Date(medicineObject.timestamp))
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
                            MaterialTheme.colorScheme.surfaceTint, shape = CircleShape
                        ), contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = medicineObject.medicineName.first().toString(),
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                }
            }
        }
    )
}

@Composable
private fun MedicineObjectCard(
    medicineObject: MedicineEntity,
    context: Context
) {
    Card(
        modifier = Modifier
            .padding(start = 20.dp, end = 20.dp, top = 20.dp)
            .fillMaxWidth(), colors = CardDefaults.cardColors(
            contentColor = MaterialTheme.colorScheme.onSurface,
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        MedicineObjectCard(
            medicineObject.medicineName, context.getString(R.string.medicine_name)
        )
        MedicineObjectCard(
            getSelectedEye(medicineObject, context),
            context.getString(R.string.medicine_eye)
        )
        MedicineObjectCard(
            medicineObject.frequency, context.getString(R.string.medicine_frequency)
        )
        MedicineObjectCard(
            medicineObject.expirationDate,
            context.getString(R.string.medicine_expiration_date)
        )
        MedicineObjectCard(
            medicineObject.specialInstruction,
            context.getString(R.string.medicine_special_instructions)
        )
    }
}

@Composable
private fun MedicineObjectCard(medicineField: String, label: String) {
    Card(
        modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Text(
            text = label,
            color = MaterialTheme.colorScheme.primary,
            fontSize = MaterialTheme.typography.titleMedium.fontSize
        )

        Text(
            text = medicineField,
            modifier = Modifier.padding(20.dp),
        )
    }
}

private fun getSelectedEye(medicine: MedicineEntity, context: Context): String {
    val message = when {
        medicine.eyeSelection.right -> context.getString(R.string.selected_right_eye)
        medicine.eyeSelection.left -> context.getString(R.string.selected_left_eye)
        medicine.eyeSelection.both -> context.getString(R.string.selected_both_eyes)
        else -> ""
    }
    return message
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
                AlertDialog(containerColor = MaterialTheme.colorScheme.primaryContainer, icon = {
                    Icon(
                        imageVector = Icons.Outlined.Warning,
                        contentDescription = "Warning Icon",
                        tint = MaterialTheme.colorScheme.error
                    )
                }, title = {
                    when (message) {
                        context.getString(R.string.delete_one_medicine_dialog) -> Text(
                            text = "Delete medicine?",
                            fontSize = MaterialTheme.typography.titleMedium.fontSize
                        )

                        context.getString(R.string.edit_one_medicine_dialog) -> Text(
                            text = "Edit medicine?",
                            fontSize = MaterialTheme.typography.titleMedium.fontSize
                        )

                        else -> Text(text = "")
                    }
                }, text = {
                    Text(
                        text = message, color = MaterialTheme.colorScheme.surfaceTint
                    )
                }, onDismissRequest = { showDialog = false }, confirmButton = {
                    TextButton(onClick = {
                        when (message) {
                            context.getString(R.string.delete_one_medicine_dialog) -> {
                                showDialog = false
                                scope.launch {
                                    viewModel.deleteMedicine(medicine?.medicineName ?: "")
                                }
                                navController.navigate(context.getString(R.string.information_screen))
                            }

                            context.getString(R.string.edit_one_medicine_dialog) -> {
                                showDialog = false
                                //TODO: ADD EDIT SCREEN and Navigate to it
                            }

                            else -> {}
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