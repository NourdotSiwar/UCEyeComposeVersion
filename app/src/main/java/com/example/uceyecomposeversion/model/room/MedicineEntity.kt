package com.example.uceyecomposeversion.model.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "medicinesDB")
data class MedicineEntity(
    @PrimaryKey @ColumnInfo(name = "medicineName") val medicineName: String,
    @ColumnInfo(name = "leftEyeSelected") val leftEyeSelected: Boolean,
    @ColumnInfo(name = "rightEyeSelected") val rightEyeSelected: Boolean,
   // @ColumnInfo(name = "bothEyesSelected") val bothEyesSelected: Boolean,
   // @ColumnInfo(name = "frequency") val frequency: String,
   // @ColumnInfo(name = "specialInstruction") val specialInstruction: String? = null,
   // @ColumnInfo(name = "expirationDate") val expirationDate: String? = null // This will be initially null and later updated by the user
)