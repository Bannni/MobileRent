package com.example.rentalapkgamesyaban.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.rentalapkgamesyaban.data.entity.RentalEntity
import com.example.rentalapkgamesyaban.data.model.RentalWithProduct
import kotlinx.coroutines.flow.Flow

@Dao
interface RentalDao {
    @Query("SELECT * FROM rentals WHERE userId = :userId ORDER BY timestamp DESC")
    fun getRentalsByUserId(userId: Int): Flow<List<RentalEntity>>

    @Query("SELECT * FROM rentals WHERE userId = :userId ORDER BY timestamp DESC")
    fun getRentalWithProducts(userId: Int): Flow<List<RentalWithProduct>>

    @Query("SELECT * FROM rentals WHERE status = 'pending' ORDER BY timestamp ASC")
    fun getAllPendingRentals(): Flow<List<RentalWithProduct>>

    @Insert
    suspend fun insertRental(rental: RentalEntity)

    @Query("UPDATE rentals SET status = :status, timestamp = :timestamp WHERE id = :id")
    suspend fun updateRentalStatus(id: Int, status: String, timestamp: Long)
    
    @Query("UPDATE rentals SET status = :status WHERE id = :id")
    suspend fun updateRentalStatusOnly(id: Int, status: String)

    @Query("SELECT COUNT(*) FROM rentals")
    fun getTotalRentalsCount(): Flow<Int>
}
