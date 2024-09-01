package com.example.uceyecomposeversion.ui.screens.qrScanner

import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.navigation.NavController
import com.budiyev.android.codescanner.AutoFocusMode
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.CodeScannerView
import com.budiyev.android.codescanner.DecodeCallback
import com.budiyev.android.codescanner.ErrorCallback
import com.budiyev.android.codescanner.ScanMode
import com.example.uceyecomposeversion.R


@Composable
fun QRScannerScreen(navController: NavController) {
    var isScannerVisible by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current
    var scanResults by remember { mutableStateOf("") }

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (!isScannerVisible) {
                Button(onClick = { isScannerVisible = true }) {
                    Text(
                        text = "Scan QR Code",
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
            } else {
                AndroidViewScanner(context = context, onScanResult = { result ->
                    isScannerVisible = false
                    scanResults = result
                    Log.d("QrCodeScannerScreen", "Scanned QR Code: $scanResults")
                    showDialog = true
                })
            }

            if (showDialog) {
                AlertDialog(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,

                    icon = {
                    Icon(
                        imageVector = Icons.Outlined.Check,
                        contentDescription = "Success Icon",
                        tint = MaterialTheme.colorScheme.surfaceTint
                    )
                },
                    text = { Text(
                        text = "QR Scanned Successfully. Continue?",
                        color = MaterialTheme.colorScheme.surfaceTint
                    ) },
                    onDismissRequest = { showDialog = false },
                    confirmButton = {
                        TextButton(onClick = { navController.navigate(context.getString(R.string.information_screen)) }) {
                            Text(
                                text = "Confirm",
                                color = MaterialTheme.colorScheme.surfaceTint
                            )
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { showDialog = false }) {
                            Text(
                                text =  "Dismiss",
                                color = MaterialTheme.colorScheme.surfaceTint
                                )
                        }
                    }
                )
            }
        }
    }

}

@Composable
fun AndroidViewScanner(onScanResult: (String) -> Unit, context: Context) {
    val lifecycleOwner = LocalLifecycleOwner.current
    var codeScanner: CodeScanner? = null

    AndroidView(modifier = Modifier.fillMaxSize(), factory = { ctx ->
        val scannerView = CodeScannerView(ctx)

        // Initialize CodeScanner
        codeScanner = CodeScanner(ctx, scannerView).apply {
            camera = CodeScanner.CAMERA_BACK
            formats = CodeScanner.TWO_DIMENSIONAL_FORMATS
            autoFocusMode = AutoFocusMode.SAFE
            scanMode = ScanMode.SINGLE
            isAutoFocusEnabled = true
            isFlashEnabled = false
            decodeCallback = DecodeCallback { result ->
                (context as? Activity)?.runOnUiThread {
                    onScanResult(result.text)
                }
            }
            errorCallback = ErrorCallback { error ->
                (context as? Activity)?.runOnUiThread {
                    Log.e("AndroidViewScanner", "Camera initialization error: ${error.message}")
                }
            }
        }

        // Start CodeScanner
        codeScanner?.startPreview()

        scannerView
    }, update = {
        // Restart scanner if the view is recreated
        codeScanner?.startPreview()
    })

    // Ensure to stop the scanner when the composable leaves the composition
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_PAUSE -> codeScanner?.releaseResources()
                Lifecycle.Event.ON_RESUME -> codeScanner?.startPreview()
                else -> {}
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
            codeScanner?.releaseResources()
        }
    }
}

