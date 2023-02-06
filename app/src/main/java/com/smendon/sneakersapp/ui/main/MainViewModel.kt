package com.smendon.sneakersapp.ui.main

import androidx.annotation.StringRes
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smendon.sneakersapp.domain.model.Sneaker
import com.smendon.sneakersapp.domain.usecase.GetAllSneakersUseCase
import com.smendon.sneakersapp.domain.usecase.GetCartCountUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    getAllSneakersUseCase: GetAllSneakersUseCase,
    private val getCartCountUseCase: GetCartCountUseCase
) : ViewModel() {

    private val _sneakersList = getAllSneakersUseCase().toMutableStateList()
    val sneakersList: List<Sneaker>
        get() = _sneakersList

    var cartCount = flow<Int> {
        getCartCountUseCase.invoke()
    }

    private val _isLoading: MutableState<Boolean> = mutableStateOf(false)
    val isLoading: State<Boolean> get() = _isLoading

    private val _selectedTab: MutableState<Int> = mutableStateOf(0)
    val selectedTab: State<Int> get() = _selectedTab

    fun selectTab(@StringRes tab: Int) {
        _selectedTab.value = tab
    }
}