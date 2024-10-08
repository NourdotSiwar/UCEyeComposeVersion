package com.example.uceyecomposeversion.model.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface MedicineDao {

    // INSERT/REPLACE NEW/EXISTING ITEM
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMedicines(medicines: List<MedicineEntity>)

    // INSERT/REPLACE NEW/EXISTING SINGLE ITEM
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMedicine(medicine: MedicineEntity)

    // GET ALL MEDICINES
    @Query("SELECT * FROM medicinesDB ORDER BY timeStamp DESC")
    fun getAllMedicinesSortedByDate(): Flow<List<MedicineEntity>>

    // GET SINGLE MEDICINE
    @Query("SELECT * FROM medicinesDB WHERE medicineName = :medicineName")
    fun getMedicineByName(medicineName: String): Flow<MedicineEntity?>

    // UPDATE SINGLE MEDICINE (May not be needed)
    // Part of a new feature to update medicine fields
    @Update
    suspend fun updateMedicine(medicine: MedicineEntity)

    // UPDATE SINGLE MEDICINE'S EXPIRATION DATE
    @Query("UPDATE medicinesDB SET expirationDate = :newExpirationDate WHERE medicineName = :medicineName")
    suspend fun updateExpirationDate(medicineName: String, newExpirationDate: String)

    // DELETE SINGLE ITEM
    @Query("DELETE FROM medicinesDB WHERE medicineName = :medicineName")
    suspend fun deleteMedicine(medicineName: String)

    // DELETE ALL MEDICINES
    @Query("DELETE FROM medicinesDB")
    suspend fun deleteAllMedicines()
}