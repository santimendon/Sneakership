package com.smendon.sneakersapp.data.datasource.local

import androidx.room.*
import com.smendon.sneakersapp.data.datasource.local.AppDatabase.Companion.TABLE_CART
import com.smendon.sneakersapp.domain.model.CartItem
import kotlinx.coroutines.flow.Flow

@Dao
interface AppDao {

    @Query("SELECT * FROM $TABLE_CART")
    fun getCartItems(): Flow<List<CartItem>>

    @Query("SELECT * FROM $TABLE_CART WHERE id = :id")
    suspend fun getCartItemById(id: Int): CartItem?

    @Query("SELECT COUNT(*) FROM $TABLE_CART")
    suspend fun getCartCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCartItem(cartItem: CartItem)

    @Delete
    suspend fun deleteCartItem(cartItem: CartItem)
}