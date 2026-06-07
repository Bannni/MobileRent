package com.example.rentalapkgamesyaban.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.rentalapkgamesyaban.data.entity.CartEntity
import com.example.rentalapkgamesyaban.data.model.CartWithProduct
import kotlinx.coroutines.flow.Flow

@Dao
interface CartDao {
    @Query("SELECT * FROM cart WHERE userId = :userId")
    fun getCartItemsByUserId(userId: Int): Flow<List<CartEntity>>

    @Query("SELECT * FROM cart WHERE userId = :userId")
    fun getCartWithProducts(userId: Int): Flow<List<CartWithProduct>>

    @Insert
    suspend fun insertCartItem(cartItem: CartEntity)

    @Delete
    suspend fun deleteCartItem(cartItem: CartEntity)

    @Query("DELETE FROM cart WHERE userId = :userId")
    suspend fun clearCart(userId: Int)
}
