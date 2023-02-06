package com.smendon.sneakersapp.ui.sneakerdetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smendon.sneakersapp.domain.model.Sneaker
import com.smendon.sneakersapp.domain.usecase.AddToCartUseCase
import com.smendon.sneakersapp.domain.usecase.GetSneakerUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SneakerDetailViewModel @Inject constructor(
    private val getSneakerUseCase: GetSneakerUseCase,
    private val addToCartUseCase: AddToCartUseCase
) : ViewModel() {

    private val _sneakerLiveData: MutableLiveData<Sneaker> = MutableLiveData()
    val sneakerLiveData: LiveData<Sneaker> = _sneakerLiveData

    private fun getSneakerDetailFromId(id: Int) {
        viewModelScope.launch {
            _sneakerLiveData.value = fetchSneakerFromRepo(id)
        }
    }

    private suspend fun fetchSneakerFromRepo(id: Int): Sneaker? {
        return withContext(Dispatchers.IO) {
            return@withContext getSneakerUseCase(id)
        }
    }

    fun loadSneakerById(id: Int) = getSneakerDetailFromId(id)

    fun addToCart(sneaker: Sneaker) {
        viewModelScope.launch(Dispatchers.IO) {
            addToCartUseCase(sneaker)
        }
    }
}