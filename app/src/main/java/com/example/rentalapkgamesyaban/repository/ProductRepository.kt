package com.example.rentalapkgamesyaban.repository

import com.example.rentalapkgamesyaban.data.dao.ProductDao
import com.example.rentalapkgamesyaban.data.entity.ProductEntity
import kotlinx.coroutines.flow.Flow

class ProductRepository(private val productDao: ProductDao) {
    fun getAllProducts(): Flow<List<ProductEntity>> = productDao.getAllProducts()

    fun searchProducts(query: String): Flow<List<ProductEntity>> = productDao.searchProducts(query)

    suspend fun getProductById(id: Int): ProductEntity? = productDao.getProductById(id)

    suspend fun insertProduct(product: ProductEntity) = productDao.insertProduct(product)

    suspend fun updateProduct(product: ProductEntity) = productDao.updateProduct(product)

    suspend fun deleteProduct(product: ProductEntity) = productDao.deleteProduct(product)
}
