package com.example.rentalapkgamesyaban.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rentalapkgamesyaban.data.model.RentalWithProduct
import com.example.rentalapkgamesyaban.repository.RentalRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class RentalViewModel(private val rentalRepository: RentalRepository) : ViewModel() {

    private val _myRentals = MutableStateFlow<List<RentalWithProduct>>(emptyList())
    val myRentals: StateFlow<List<RentalWithProduct>> = _myRentals.asStateFlow()

    val pendingRentals: StateFlow<List<RentalWithProduct>> = rentalRepository.getAllPendingRentals()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val totalOrdersCount: StateFlow<Int> = rentalRepository.getTotalRentalsCount()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = 0
        )

    fun loadMyRentals(userId: Int) {
        viewModelScope.launch {
            rentalRepository.getRentalWithProducts(userId).collectLatest { rentals ->
                rentalRepository.checkAndExpireRentals(rentals)
                _myRentals.value = rentals
            }
        }
    }

    fun approveRental(id: Int) {
        viewModelScope.launch {
            rentalRepository.approveRental(id)
        }
    }
}
