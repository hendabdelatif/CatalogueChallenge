package com.hend.cataloguechallenge.persistance.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Entity classes used in database
 */
@Entity(primaryKeys = ["id"])
data class Category(
    val id: String,
    val name: String,
    val description: String,
    val products : List<Product>
)

@Entity(primaryKeys = ["name"])
data class Product(
    val id: String,
    val categoryId : String,
    val name : String,
    val url : String,
    val description: String,
    val salePrice : Price
)

@Entity
data class Price(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    val amount : String,
    val currency : String
)
