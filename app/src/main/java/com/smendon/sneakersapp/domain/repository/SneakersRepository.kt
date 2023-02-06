package com.smendon.sneakersapp.domain.repository

import com.smendon.sneakersapp.domain.model.CartItem
import com.smendon.sneakersapp.domain.model.Sneaker
import kotlinx.coroutines.flow.Flow

interface SneakersRepository {
    fun getListOfSneakers(): List<Sneaker>

    suspend fun getSneakerById(id: Int): Sneaker?

    fun getCartItems(): Flow<List<CartItem>>

    suspend fun getCartItemById(id: Int): CartItem?

    suspend fun getCartCount(): Int

    suspend fun insertCartItem(cartItem: CartItem)

    suspend fun deleteCartItem(cartItem: CartItem)
}