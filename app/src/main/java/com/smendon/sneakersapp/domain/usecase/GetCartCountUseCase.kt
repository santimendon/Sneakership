package com.smendon.sneakersapp.domain.usecase

import com.smendon.sneakersapp.domain.repository.SneakersRepository
import javax.inject.Inject

class GetCartCountUseCase @Inject constructor(
    private val repository: SneakersRepository
) {
    suspend operator fun invoke(): Int {
        return repository.getCartCount()
    }
}