package com.smendon.sneakersapp.ui.cart

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.AsyncImage
import com.smendon.sneakersapp.R
import com.smendon.sneakersapp.domain.model.CartItem
import com.smendon.sneakersapp.ui.theme.Salmon


@Composable
fun Cart(
    cartViewModel: CartViewModel,
    pressOnBack: () -> Unit = {}
) {
    val cartItemList = cartViewModel.cartItems.toMutableStateList()
    LaunchedEffect(key1 = true) {
        cartViewModel.getCartItems()
    }
    val totalCartValue by cartViewModel.cartTotalPriceState
    val isCtaButtonEnabled by cartViewModel.isCtaButtonEnabledState

    CartView(
        cartItemList = cartItemList,
        totalPrice = totalCartValue,
        isCtaButtonEnabled = isCtaButtonEnabled,
        pay = cartViewModel::pay,
        removeFromCart = cartViewModel::removeFromCart,
        pressOnBack = pressOnBack
    )
}

@Composable
fun CartView(
    cartItemList: List<CartItem>,
    totalPrice: Double,
    isCtaButtonEnabled: Boolean,
    pay: () -> Unit,
    removeFromCart: (CartItem) -> Unit,
    pressOnBack: () -> Unit
) {
    Column {
        CartHeader(pressOnBack)
        CartList(cartItemList, removeFromCart)
        CartFooter(totalPrice, isCtaButtonEnabled, pay)
    }
}

@Composable
fun CartHeader(
    pressOnBack: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(40.dp)
    ) {
        Text(
            text = stringResource(R.string.label_cart),
            style = MaterialTheme.typography.h3
        )
    }
}

@Composable
fun CartList(
    cartItemList: List<CartItem>,
    removeFromCart: (CartItem) -> Unit
) {
    val listState = rememberLazyListState()
    Column(Modifier.background(MaterialTheme.colors.background)) {
        LazyColumn(
            state = listState,
            contentPadding = PaddingValues(4.dp)
        ) {
            items(
                items = cartItemList,
                key = { it.id!! },
                itemContent = { cartItem ->
                    CartItem(
                        cartItem = cartItem,
                        removeFromCart = { removeFromCart(cartItem) },
                    )
                }
            )
        }
    }
}

@Composable
private fun CartItem(
    cartItem: CartItem,
    removeFromCart: (CartItem) -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
            .clickable(
                onClick = { }
            ),
        color = MaterialTheme.colors.onBackground,
        elevation = 8.dp,
        shape = RoundedCornerShape(8.dp)
    ) {
        ConstraintLayout(
            modifier = Modifier.padding(8.dp)
        ) {

            val (image, title, price, quantity, removeBtn) = createRefs()
            AsyncImage(model = cartItem.image,
                contentDescription = cartItem.name,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .size(100.dp)
                    .aspectRatio(1.0f)
                    .clip(RoundedCornerShape(4.dp))
                    .constrainAs(image) {
                        centerVerticallyTo(parent)
                        end.linkTo(title.start)
                    })

            Text(
                modifier = Modifier
                    .constrainAs(title) {
                        start.linkTo(image.end)
                    }
                    .padding(horizontal = 12.dp),
                text = cartItem.name,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.h1
            )

            Text(
                modifier = Modifier
                    .constrainAs(price) {
                        start.linkTo(image.end)
                        top.linkTo(title.bottom)
                    }
                    .padding(start = 12.dp, top = 4.dp),
                text = "₹${cartItem.price}",
                style = MaterialTheme.typography.body2,
            )

            Text(
                modifier = Modifier
                    .constrainAs(quantity) {
                        start.linkTo(image.end)
                        top.linkTo(price.bottom)
                    }
                    .padding(start = 12.dp, top = 4.dp),
                text = "${cartItem.quantity}",
                style = MaterialTheme.typography.body2,
            )

            IconButton(
                onClick = { removeFromCart(cartItem) },
                modifier = Modifier
                    .constrainAs(removeBtn) {
                        start.linkTo(image.end)
                        top.linkTo(parent.top)
                        end.linkTo(parent.end)
                        start.linkTo(title.end)
                    }
            ) {
                Icon(
                    Icons.Filled.Delete,
                    null,
                    tint = Salmon
                )
            }
        }
    }
}

@Composable
fun CartFooter(
    totalPrice: Double,
    isCtaButtonEnabled: Boolean,
    pay: () -> Unit,
) {
    Column(
    ) {
        ConstraintLayout(
            modifier = Modifier.padding(8.dp)
        ) {
            val (subtitle, labeltotal, price, checkoutBtn) = createRefs()
            Text(
                text = stringResource(R.string.label_order_details),
                style = MaterialTheme.typography.h1,
                modifier = Modifier
                    .constrainAs(subtitle) {
                        top.linkTo(parent.bottom)
                    }
                    .padding(start = 16.dp, top = 12.dp)
            )

            Text(
                text = stringResource(R.string.label_total),
                style = MaterialTheme.typography.body2,
                modifier = Modifier
                    .constrainAs(labeltotal) {
                        top.linkTo(subtitle.bottom)
                        start.linkTo(parent.start)
                    }
                    .padding(16.dp)
            )

            Text(
                text = "$totalPrice",
                style = MaterialTheme.typography.body1,
                modifier = Modifier
                    .constrainAs(price) {
                        top.linkTo(subtitle.bottom)
                        start.linkTo(labeltotal.end)
                    }
                    .padding(top = 16.dp)
            )

            Button(
                onClick = { pay() },
                colors = ButtonDefaults.buttonColors(backgroundColor = Salmon),
                modifier = Modifier
                    .constrainAs(checkoutBtn) {
                        top.linkTo(subtitle.bottom)
                        start.linkTo(price.end, 16.dp)
                    }
            ) {
                Text(text = stringResource(R.string.label_checkout), color = Color.White)
            }
        }
    }

}