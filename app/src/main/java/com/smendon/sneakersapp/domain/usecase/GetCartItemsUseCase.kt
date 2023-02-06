package com.smendon.sneakersapp.domain.usecase

import com.smendon.sneakersapp.domain.model.CartItem
import com.smendon.sneakersapp.domain.repository.SneakersRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCartItemsUseCase @Inject constructor(
    private val repository: SneakersRepository
) {
    operator fun invoke(): Flow<List<CartItem>> {
        return repository.getCartItems()
    }
}