package com.example.rentalapkgamesyaban.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rentalapkgamesyaban.data.entity.CartEntity
import com.example.rentalapkgamesyaban.data.entity.RentalEntity
import com.example.rentalapkgamesyaban.data.model.CartWithProduct
import com.example.rentalapkgamesyaban.repository.CartRepository
import com.example.rentalapkgamesyaban.repository.RentalRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class CartViewModel(
    private val cartRepository: CartRepository,
    private val rentalRepository: RentalRepository
) : ViewModel() {

    private val _cartItems = MutableStateFlow<List<CartWithProduct>>(emptyList())
    val cartItems: StateFlow<List<CartWithProduct>> = _cartItems.asStateFlow()

    fun loadCart(userId: Int) {
        viewModelScope.launch {
            cartRepository.getCartWithProducts(userId).collectLatest {
                _cartItems.value = it
            }
        }
    }

    fun addToCart(userId: Int, productId: Int) {
        viewModelScope.launch {
            cartRepository.addToCart(userId, productId)
        }
    }

    fun removeFromCart(cartItem: CartEntity) {
        viewModelScope.launch {
            cartRepository.removeFromCart(cartItem)
        }
    }

    fun checkout(userId: Int, notes: String) {
        viewModelScope.launch {
            val currentItems = _cartItems.value
            for (item in currentItems) {
                val rental = RentalEntity(
                    userId = userId,
                    productId = item.product.id,
                    status = "pending",
                    notes = notes,
                    timestamp = System.currentTimeMillis()
                )
                rentalRepository.insertRental(rental)
            }
            cartRepository.clearCart(userId)
        }
    }
}
