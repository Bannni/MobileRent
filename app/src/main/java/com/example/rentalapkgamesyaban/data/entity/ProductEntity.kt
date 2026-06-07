package com.example.rentalapkgamesyaban.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class ProductEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val platform: String, // "game" or "android"
    val description: String,
    val price: Double,
    val externalLink: String, // Link URL
    val imageUri: String // Empty or URL
)
