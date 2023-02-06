package com.smendon.sneakersapp.domain.usecase

import com.smendon.sneakersapp.domain.model.Sneaker
import com.smendon.sneakersapp.domain.repository.SneakersRepository
import javax.inject.Inject

class AddToCartUseCase @Inject constructor(
    private val repository: SneakersRepository
) {
    suspend operator fun invoke(sneaker: Sneaker) {
        return repository.insertCartItem(sneaker.toCartItem())
    }
}