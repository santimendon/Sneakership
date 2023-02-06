package com.smendon.sneakersapp.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.smendon.sneakersapp.data.datasource.local.AppDatabase.Companion.TABLE_CART

@Entity(tableName = TABLE_CART)
data class CartItem(
    @PrimaryKey val id: Int? = null,
    val name: String,
    val price: Double,
    val currencyCode: String,
    val image: String,
    val quantity: Int
)