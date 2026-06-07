package com.example.rentalapkgamesyaban.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.rentalapkgamesyaban.repository.CartRepository
import com.example.rentalapkgamesyaban.repository.ProductRepository
import com.example.rentalapkgamesyaban.repository.RentalRepository
import com.example.rentalapkgamesyaban.repository.UserRepository

class ViewModelFactory(
    private val userRepository: UserRepository,
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository,
    private val rentalRepository: RentalRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(AuthViewModel::class.java) -> {
                AuthViewModel(userRepository) as T
            }
            modelClass.isAssignableFrom(ProductViewModel::class.java) -> {
                ProductViewModel(productRepository) as T
            }
            modelClass.isAssignableFrom(CartViewModel::class.java) -> {
                CartViewModel(cartRepository, rentalRepository) as T
            }
            modelClass.isAssignableFrom(RentalViewModel::class.java) -> {
                RentalViewModel(rentalRepository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
