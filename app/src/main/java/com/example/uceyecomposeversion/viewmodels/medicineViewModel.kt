package com.example.uceyecomposeversion.viewmodels

import android.app.Application
import android.icu.text.SimpleDateFormat
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.uceyecomposeversion.model.room.AppApplication
import com.example.uceyecomposeversion.model.room.MedicineEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit


class medicineViewModel(private val application: Application) :
    AndroidViewModel(application = application) {

    private val medicineDao = AppApplication(application).database.medicineDao()
    private val _medicines = MutableStateFlow<List<MedicineEntity>>(emptyList())
    val medicines: StateFlow<List<MedicineEntity>> = _medicines

    private val json = Json { ignoreUnknownKeys = true }

    init {
        viewModelScope.launch {
            medicineDao.getAllMedicinesSortedByDate().collect { medicineList ->
                _medicines.value = medicineList
            }
        }
    }

    fun insertMedicines(jsonString: String) {
        viewModelScope.launch {
            try {
                val medicines: List<MedicineEntity> = json.decodeFromString(jsonString)
                medicines.forEach { medicine ->
                    val medicineRecord = MedicineEntity(
                        medicineName = medicine.medicineName,
                        leftEyeSelected = medicine.leftEyeSelected,
                        rightEyeSelected = medicine.rightEyeSelected,
                        bothEyesSelected = medicine.bothEyesSelected,
                        frequency = medicine.frequency,
                        specialInstruction = medicine.specialInstruction,
                        expirationDate = medicine.expirationDate,
                        timestamp = Date().time
                    )
                    medicineDao.insertMedicine(medicineRecord)
                }

            } catch (e: Exception) {
                Log.e(
                    "QrViewModel", "Error handling QR Scan Result: ${e.message} due to ${e.cause}"
                )
            }
        }
    }

    fun getMedicineByName(name: String): Flow<MedicineEntity?> {
        return medicineDao.getMedicineByName(name)
    }

    suspend fun deleteMedicine(medicineName: String) {
            medicineDao.deleteMedicine(medicineName)
    }

    suspend fun deleteMedicines() {
        medicineDao.deleteAllMedicines()
    }

    fun getRelativeTime(date: Date): String {
        val now = Date()
        val diffMillis = now.time - date.time
        val diffSeconds = TimeUnit.MILLISECONDS.toSeconds(diffMillis)
        val diffMinutes = TimeUnit.MILLISECONDS.toMinutes(diffMillis)
        val diffHours = TimeUnit.MILLISECONDS.toHours(diffMillis)
        val diffDays = TimeUnit.MILLISECONDS.toDays(diffMillis)

        return when {
            diffSeconds < 60 -> "Just now"
            diffMinutes < 60 -> "${diffMinutes}m ago"
            diffHours < 24 -> "${diffHours}h ago"
            diffDays == 1L -> "Yesterday"
            diffDays < 30 -> "${diffDays}d ago"
            else -> SimpleDateFormat("MMM d", Locale.getDefault()).format(date)
        }
    }

}