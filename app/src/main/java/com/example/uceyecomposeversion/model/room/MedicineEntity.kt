package com.example.uceyecomposeversion.model.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Entity(tableName = "medicinesDB")
@Serializable
data class MedicineEntity(
    @PrimaryKey @ColumnInfo(name = "medicineName") val medicineName: String,
    @ColumnInfo(name = "leftEyeSelected") val leftEyeSelected: Boolean = false,
    @ColumnInfo(name = "rightEyeSelected") val rightEyeSelected: Boolean = false,
    @ColumnInfo(name = "bothEyesSelected") val bothEyesSelected: Boolean = false,
    @ColumnInfo(name = "frequency") val frequency: String,
    @ColumnInfo(name = "specialInstruction") val specialInstruction: String = "N/A",
    @ColumnInfo(name = "expirationDate") val expirationDate: String = "N/A", // This will be initially null and later updated by the user
    @ColumnInfo(name = "timeStamp") val timestamp: Long = 0
)