package com.example.uceyecomposeversion.model.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable
import java.util.Date

@Entity(tableName = "medicinesDB")
@Serializable
data class MedicineEntity(
    @PrimaryKey @ColumnInfo(name = "medicineName") val medicineName: String,
    @ColumnInfo(name = "eyeSelection") val eyeSelection: Eye,
    @ColumnInfo(name = "frequency") val frequency: String,
    @ColumnInfo(name = "specialInstruction") val specialInstruction: String = "N/A",
    @ColumnInfo(name = "expirationDate") val expirationDate: String = "N/A", // This will be initially null and later updated by the user
    @ColumnInfo(name = "timeStamp") val timestamp: Long = Date().time
)

@Serializable
data class Eye(
    val left: Boolean = false,
    val right: Boolean = false,
    val both: Boolean = false,
)