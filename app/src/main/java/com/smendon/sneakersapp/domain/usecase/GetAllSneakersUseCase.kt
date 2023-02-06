package com.smendon.sneakersapp.domain.usecase

import com.smendon.sneakersapp.domain.model.Sneaker
import com.smendon.sneakersapp.domain.repository.SneakersRepository
import javax.inject.Inject

class GetAllSneakersUseCase @Inject constructor(
    private val repository: SneakersRepository
) {
    operator fun invoke(): List<Sneaker> {
        return repository.getListOfSneakers()
    }
}