package com.smendon.sneakersapp.data.repository

import com.smendon.sneakersapp.data.datasource.local.AppDao
import com.smendon.sneakersapp.data.datasource.remote.RemoteData
import com.smendon.sneakersapp.domain.model.CartItem
import com.smendon.sneakersapp.domain.model.Sneaker
import com.smendon.sneakersapp.domain.repository.SneakersRepository
import kotlinx.coroutines.flow.Flow

class SneakersRepositoryImpl constructor(
    private val remoteData: RemoteData,
    private val dao: AppDao
) : SneakersRepository {

    override fun getListOfSneakers(): List<Sneaker> = remoteData.getAllSneakers()

    override suspend fun getSneakerById(id: Int): Sneaker? = remoteData.getSneakerById(id)

    override fun getCartItems(): Flow<List<CartItem>> = dao.getCartItems()

    override suspend fun getCartItemById(id: Int): CartItem? = dao.getCartItemById(id)

    override suspend fun getCartCount(): Int = dao.getCartCount()

    override suspend fun insertCartItem(cartItem: CartItem) {
        dao.insertCartItem(cartItem)
    }

    override suspend fun deleteCartItem(cartItem: CartItem) {
        dao.deleteCartItem(cartItem)
    }
}