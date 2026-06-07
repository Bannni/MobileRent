package com.example.rentalapkgamesyaban.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rentalapkgamesyaban.data.entity.ProductEntity
import com.example.rentalapkgamesyaban.repository.ProductRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ProductViewModel(private val productRepository: ProductRepository) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    val products: StateFlow<List<ProductEntity>> = _searchQuery.flatMapLatest { query ->
        if (query.isBlank()) {
            productRepository.getAllProducts()
        } else {
            productRepository.searchProducts(query)
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun addProduct(name: String, platform: String, description: String, price: Double, externalLink: String, imageUri: String) {
        viewModelScope.launch {
            productRepository.insertProduct(
                ProductEntity(
                    name = name,
                    platform = platform,
                    description = description,
                    price = price,
                    externalLink = externalLink,
                    imageUri = imageUri
                )
            )
        }
    }

    fun updateProduct(product: ProductEntity) {
        viewModelScope.launch {
            productRepository.updateProduct(product)
        }
    }

    fun deleteProduct(product: ProductEntity) {
        viewModelScope.launch {
            productRepository.deleteProduct(product)
        }
    }
}
