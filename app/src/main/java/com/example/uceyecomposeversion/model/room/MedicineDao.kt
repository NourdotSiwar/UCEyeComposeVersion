package com.example.uceyecomposeversion.model.room

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface MedicineDao {


//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    fun insert(medicineEntity: Entity): Long

//    @Transaction
//    fun insertMedicine(medicineEntities: List<Entity>) {
//        medicineEntities.forEach {
//            insert(it)
//        }
//    }

    // Prescription may contain 1 or more medicines
    @Query("SELECT * FROM medicinesDB")
    fun getPrescription(): Flow<List<MedicineEntity>>

    @Query("SELECT * FROM medicinesDB WHERE medicineName = :medicineName LIMIT 1")
    fun findByMedicineName(medicineName: String): MedicineEntity?

//    //getMedicine() is used for the expiration date
//    @Query("SELECT * FROM medicinesDB WHERE medicineName = :medicineName LIMIT 1")
//    fun getMedicine(medicineName: String): Entity?
//
//    @Query("UPDATE medicinesDB SET expirationDate = :newExpirationDate WHERE medicineName = :medicineName")
//    suspend fun editExpirationDate(medicineName: String, newExpirationDate: String)

//    @Query("DELETE FROM medicinesDB WHERE medicineName IN (:medicines)")
//    suspend fun deletePrescription(medicines: List<String>)
//
//    @Query("DELETE FROM medicinesDB WHERE medicineName = :medicineName")
//    suspend fun deleteMedicine(medicineName: String)

    // TODO: editMedicine() <- entirely near feature
}