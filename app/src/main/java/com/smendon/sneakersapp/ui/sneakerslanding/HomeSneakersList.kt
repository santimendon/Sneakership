package com.smendon.sneakersapp.ui.sneakerslanding

import androidx.annotation.StringRes
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import com.smendon.sneakersapp.R
import com.smendon.sneakersapp.ui.cart.Cart
import com.smendon.sneakersapp.ui.main.MainViewModel
import com.smendon.sneakersapp.ui.theme.GreyDark
import com.smendon.sneakersapp.ui.theme.Salmon


@Composable
fun HomeSneakersList(
    viewModel: MainViewModel, selectSneaker: (Int) -> Unit
) {

    val sneakers = viewModel.sneakersList
    val isLoading: Boolean by viewModel.isLoading
    val cartCount = viewModel.cartCount.collectAsState(initial = 0)
    val selectedTab = SneakersHomeTab.getTabFromResource(viewModel.selectedTab.value)
    val tabs = SneakersHomeTab.values()

    ConstraintLayout {
        val (body, progress) = createRefs()
        Scaffold(topBar = { SneakersAppBar() }, modifier = Modifier.constrainAs(body) {
            top.linkTo(parent.top)
        }, bottomBar = {
            BottomNavigation(
                elevation = 32.dp,
                backgroundColor = Color.White,
                modifier = Modifier
                    .navigationBarsPadding()
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(25.dp, 25.dp, 0.dp, 0.dp)),
            ) {
                tabs.forEach { tab ->
                    BottomNavigationItem(
                        icon = {
                            if ("CART".equals(tab.name)) {
                                BadgedBox(
                                    badge = { Badge { Text("0") } },
                                    modifier = Modifier.background(Color.White)
                                ) {
                                    Icon(
                                        imageVector = tab.icon, contentDescription = null
                                    )
                                }
                            } else {
                                Icon(
                                    imageVector = tab.icon, contentDescription = null
                                )
                            }
                        },
                        label = {
                            Text(
                                text = stringResource(tab.title), color = Color.Black
                            )
                        },
                        selected = tab == selectedTab,
                        onClick = { viewModel.selectTab(tab.title) },
                        selectedContentColor = Salmon,
                        unselectedContentColor = GreyDark,
                    )
                }
            }
        }) { innerPadding ->
            val modifier = Modifier.padding(innerPadding)
            Crossfade(selectedTab) { destination ->
                when (destination) {
                    SneakersHomeTab.HOME -> HomeSneakers(modifier, sneakers, selectSneaker)
                    SneakersHomeTab.CART -> Cart(cartViewModel = hiltViewModel())
                }
            }
        }
        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.constrainAs(progress) {
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            })
        }
    }
}

@Preview
@Composable
private fun SneakersAppBar() {
    TopAppBar(
        elevation = 12.dp,
        backgroundColor = Color.White,
        modifier = Modifier
            .statusBarsPadding()
            .height(60.dp)
    ) {
        Text(
            modifier = Modifier
                .padding(8.dp)
                .align(Alignment.CenterVertically),
            text = stringResource(R.string.app_name),
            style = MaterialTheme.typography.h1,
            color = Salmon
        )
    }
}

enum class SneakersHomeTab(
    @StringRes val title: Int, val icon: ImageVector
) {
    HOME(R.string.menu_home, Icons.Filled.Home),
    CART(R.string.menu_cart, Icons.Filled.ShoppingCart);

    companion object {
        fun getTabFromResource(@StringRes resource: Int): SneakersHomeTab {
            return when (resource) {
                R.string.menu_cart -> CART
                else -> HOME
            }
        }
    }
}