package com.example.rentalapkgamesyaban.data.model

import androidx.room.Embedded
import androidx.room.Relation
import com.example.rentalapkgamesyaban.data.entity.CartEntity
import com.example.rentalapkgamesyaban.data.entity.ProductEntity

data class CartWithProduct(
    @Embedded val cart: CartEntity,
    @Relation(
        parentColumn = "productId",
        entityColumn = "id"
    )
    val product: ProductEntity
)
