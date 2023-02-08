package com.smendon.sneakersapp.domain.usecase

import com.smendon.sneakersapp.domain.model.CartItem
import com.smendon.sneakersapp.domain.repository.SneakersRepository
import javax.inject.Inject

class CartCheckoutUseCase @Inject constructor(
    private val repository: SneakersRepository
) {
    suspend operator fun invoke(cartItems: List<CartItem>, totalCartValue: Double) {
        return repository.cartCheckout(cartItems, totalCartValue)
    }
}