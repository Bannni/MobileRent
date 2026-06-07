package com.example.rentalapkgamesyaban.data.model

import androidx.room.Embedded
import androidx.room.Relation
import com.example.rentalapkgamesyaban.data.entity.ProductEntity
import com.example.rentalapkgamesyaban.data.entity.RentalEntity

data class RentalWithProduct(
    @Embedded val rental: RentalEntity,
    @Relation(
        parentColumn = "productId",
        entityColumn = "id"
    )
    val product: ProductEntity
)
