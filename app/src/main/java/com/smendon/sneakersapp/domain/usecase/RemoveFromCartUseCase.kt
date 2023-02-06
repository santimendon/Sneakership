package com.smendon.sneakersapp.domain.usecase

import com.smendon.sneakersapp.domain.model.CartItem
import com.smendon.sneakersapp.domain.repository.SneakersRepository
import javax.inject.Inject

class RemoveFromCartUseCase @Inject constructor(
    private val repository: SneakersRepository
) {
    suspend operator fun invoke(cartItem: CartItem) {
        return repository.deleteCartItem(cartItem)
    }
}