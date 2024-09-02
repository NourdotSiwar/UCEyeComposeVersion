package com.example.uceyecomposeversion.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.uceyecomposeversion.model.room.AppApplication
import com.example.uceyecomposeversion.model.room.MedicineEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json


class QrViewModel(private val application: Application) :
    AndroidViewModel(application = application) {

    private val medicineDao = AppApplication(application).database.medicineDao()
    private val _medicines = MutableStateFlow<List<MedicineEntity>>(emptyList())
    val medicines: StateFlow<List<MedicineEntity>> = _medicines
    val json = Json { ignoreUnknownKeys = true }

    init {
        viewModelScope.launch {
            medicineDao.getAllMedicines().collect { medicineList ->
                _medicines.value = medicineList
            }
        }
    }

    fun insertQrScanResult(jsonString: String) {
        viewModelScope.launch {
            try {
                val medicines: List<MedicineEntity> = json.decodeFromString(jsonString)
                medicineDao.insertMedicines(medicines)
            } catch (e: Exception) {
                Log.e(
                    "QrViewModel",
                    "Error handling QR Scan Result: ${e.message} due to ${e.cause}"
                )
            }
        }
    }
}