package com.example.rentalapkgamesyaban.repository

import com.example.rentalapkgamesyaban.data.dao.CartDao
import com.example.rentalapkgamesyaban.data.entity.CartEntity
import com.example.rentalapkgamesyaban.data.model.CartWithProduct
import kotlinx.coroutines.flow.Flow

class CartRepository(private val cartDao: CartDao) {
    fun getCartWithProducts(userId: Int): Flow<List<CartWithProduct>> = cartDao.getCartWithProducts(userId)

    suspend fun addToCart(userId: Int, productId: Int) {
        cartDao.insertCartItem(CartEntity(userId = userId, productId = productId))
    }

    suspend fun removeFromCart(cartItem: CartEntity) {
        cartDao.deleteCartItem(cartItem)
    }

    suspend fun clearCart(userId: Int) {
        cartDao.clearCart(userId)
    }
}
