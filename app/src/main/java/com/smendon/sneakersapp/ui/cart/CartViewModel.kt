package com.smendon.sneakersapp.ui.cart

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smendon.sneakersapp.domain.model.CartItem
import com.smendon.sneakersapp.domain.usecase.GetCartItemsUseCase
import com.smendon.sneakersapp.domain.usecase.RemoveFromCartUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val getCartItemsUseCase: GetCartItemsUseCase,
    private val removeFromCartUseCase: RemoveFromCartUseCase
) : ViewModel() {

    var cartItems by mutableStateOf(emptyList<CartItem>())

    val cartTotalPriceState: State<Double>
        get() = mutableStateOf(cartItems.sumOf { cartItem ->
            cartItem.price * cartItem.quantity
        })

    val isCtaButtonEnabledState: State<Boolean>
        get() = mutableStateOf(!cartItems.isEmpty())

    fun getCartItems() {
        viewModelScope.launch {
            getCartItemsUseCase.invoke().collect { items ->
                cartItems = items
            }
        }
    }

    fun pay() {
    }

    fun removeFromCart(cartItem: CartItem) {
        viewModelScope.launch(Dispatchers.IO) {
            removeFromCartUseCase.invoke(cartItem)
        }
    }
}