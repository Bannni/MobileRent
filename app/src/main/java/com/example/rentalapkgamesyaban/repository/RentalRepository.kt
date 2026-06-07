package com.example.rentalapkgamesyaban.repository

import com.example.rentalapkgamesyaban.data.dao.RentalDao
import com.example.rentalapkgamesyaban.data.entity.RentalEntity
import com.example.rentalapkgamesyaban.data.model.RentalWithProduct
import kotlinx.coroutines.flow.Flow

class RentalRepository(private val rentalDao: RentalDao) {
    fun getRentalWithProducts(userId: Int): Flow<List<RentalWithProduct>> = rentalDao.getRentalWithProducts(userId)
    
    fun getAllPendingRentals(): Flow<List<RentalWithProduct>> = rentalDao.getAllPendingRentals()
    
    fun getTotalRentalsCount(): Flow<Int> = rentalDao.getTotalRentalsCount()

    suspend fun insertRental(rental: RentalEntity) = rentalDao.insertRental(rental)

    suspend fun approveRental(id: Int) {
        rentalDao.updateRentalStatus(id, "active", System.currentTimeMillis())
    }
    
    suspend fun expireRental(id: Int) {
        rentalDao.updateRentalStatusOnly(id, "expired")
    }

    suspend fun checkAndExpireRentals(rentals: List<RentalWithProduct>) {
        val sevenDaysMs = 7 * 24 * 60 * 60 * 1000L
        val currentTime = System.currentTimeMillis()
        for (rentalWithProduct in rentals) {
            val rental = rentalWithProduct.rental
            if (rental.status == "active" && (currentTime - rental.timestamp > sevenDaysMs)) {
                expireRental(rental.id)
            }
        }
    }
}
