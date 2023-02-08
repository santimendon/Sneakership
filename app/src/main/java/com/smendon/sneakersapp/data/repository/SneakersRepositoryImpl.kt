package com.smendon.sneakersapp.data.repository

import com.smendon.sneakersapp.branch.BranchHelper
import com.smendon.sneakersapp.data.datasource.local.AppDao
import com.smendon.sneakersapp.data.datasource.remote.RemoteData
import com.smendon.sneakersapp.domain.model.CartItem
import com.smendon.sneakersapp.domain.model.Sneaker
import com.smendon.sneakersapp.domain.repository.SneakersRepository
import kotlinx.coroutines.flow.Flow

class SneakersRepositoryImpl constructor(
    private val remoteData: RemoteData,
    private val dao: AppDao,
    private val branchHelper: BranchHelper
) : SneakersRepository {

    override fun getListOfSneakers(): List<Sneaker> = remoteData.getAllSneakers()

    override suspend fun getSneakerById(id: Int): Sneaker? {
        val sneaker = remoteData.getSneakerById(id)
        sneaker?.let { branchHelper.tagViewItem(it) }
        return sneaker
    }

    override fun getCartItems(): Flow<List<CartItem>> = dao.getCartItems()

    override suspend fun getCartItemById(id: Int): CartItem? = dao.getCartItemById(id)

    override suspend fun getCartCount(): Int = dao.getCartCount()

    override suspend fun insertCartItem(cartItem: CartItem) {
        branchHelper.tagAddToCart(cartItem)
        dao.insertCartItem(cartItem)
    }

    override suspend fun deleteCartItem(cartItem: CartItem) {
        branchHelper.tagDeleteFromCart(cartItem)
        dao.deleteCartItem(cartItem)
    }

    override fun cartCheckout(cartItems: List<CartItem>, totalCartValue: Double) {
        branchHelper.tagCartCheckout(cartItems, totalCartValue)
    }
}