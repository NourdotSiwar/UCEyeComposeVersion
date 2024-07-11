package com.example.uceyecomposeversion.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow


class QRCameraViewModel(application: Application) : AndroidViewModel(application = application) {

    // StateFlow represents a board with a message.
    // UiState is the message.
    // The ViewModel writes and updates this message.
    // Private uiState for when the viewmodel writes a new message
    // Public uiState for when the composable functions can see the message but not change it

    private val _uiState = MutableStateFlow<UiState?>(null)
    val uiState: StateFlow<UiState?> get() = _uiState

    data class UiState(val message: String?)

    fun onDecode(result: String) {
        _uiState.value = UiState(message = "Scan result: $result")
    }

    fun onError(error: String?) {
        _uiState.value = UiState(message = "Camera initialization error: $error")
    }

    fun clearMessage() {
        _uiState.value = null
    }
}