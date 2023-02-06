package com.smendon.sneakersapp.ui.sneakerdetails

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.AsyncImage
import com.smendon.sneakersapp.R
import com.smendon.sneakersapp.domain.model.Sneaker
import com.smendon.sneakersapp.ui.theme.Salmon

@Composable
fun SneakerDetail(
    sneakerId: Int,
    sneakerDetailViewModel: SneakerDetailViewModel,
    pressOnBack: () -> Unit = {}
) {

    sneakerDetailViewModel.loadSneakerById(sneakerId)
    val sneaker: Sneaker? by sneakerDetailViewModel.sneakerLiveData.observeAsState()
    val mContext = LocalContext.current
    sneaker?.let { it ->
        SneakerDetailsBody(
            it,
            addToCart = {
                sneakerDetailViewModel.addToCart(it)
                Toast.makeText(mContext, "${it.name} added to cart", Toast.LENGTH_SHORT).show()
            },
            pressOnBack
        )
    }
}

@Composable
private fun SneakerDetailsBody(
    sneaker: Sneaker,
    addToCart: (Sneaker) -> Unit,
    pressOnBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .background(MaterialTheme.colors.background)
            .fillMaxHeight()
    ) {

        ConstraintLayout {
            val (arrow, image, title, size, color, labelprice, price, addBtn) = createRefs()

            AsyncImage(
                model = sneaker.image,
                contentDescription = sneaker.name,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .constrainAs(image) {
                        top.linkTo(parent.top)
                    }
                    .fillMaxWidth()
                    .aspectRatio(0.85f),
            )

            Text(
                text = sneaker.name,
                style = MaterialTheme.typography.h1,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                modifier = Modifier
                    .constrainAs(title) {
                        top.linkTo(image.bottom)
                    }
                    .padding(start = 16.dp, top = 12.dp)
            )

            Text(
                text = "Size: 8",
                style = MaterialTheme.typography.body2,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                modifier = Modifier
                    .constrainAs(size) {
                        top.linkTo(title.bottom)
                    }
                    .padding(start = 16.dp, top = 12.dp)
            )

            Text(
                text = "Color: Black",
                style = MaterialTheme.typography.body2,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                modifier = Modifier
                    .constrainAs(color) {
                        top.linkTo(size.bottom)
                    }
                    .padding(start = 16.dp, top = 12.dp)
            )

            Text(
                text = stringResource(R.string.label_price),
                style = MaterialTheme.typography.body2,
                modifier = Modifier
                    .constrainAs(labelprice) {
                        top.linkTo(color.bottom)
                        start.linkTo(parent.start)
                    }
                    .padding(16.dp)
            )

            Text(
                text = "₹${sneaker.price}",
                style = MaterialTheme.typography.body1,
                modifier = Modifier
                    .constrainAs(price) {
                        top.linkTo(color.bottom)
                        start.linkTo(labelprice.end)
                    }
                    .padding(top = 16.dp)
            )

            Button(
                onClick = {
                    addToCart(sneaker)
                },
                colors = ButtonDefaults.buttonColors(backgroundColor = Salmon),
                modifier = Modifier
                    .constrainAs(addBtn) {
                        top.linkTo(color.bottom)
                        end.linkTo(parent.end, 16.dp)
                    }
            ) {
                Text(text = stringResource(R.string.label_add_to_cart), color = Color.White)
            }

            Icon(
                imageVector = Icons.Filled.ArrowBack,
                tint = Salmon,
                contentDescription = null,
                modifier = Modifier
                    .constrainAs(arrow) {
                        top.linkTo(parent.top)
                    }
                    .padding(12.dp)
                    .statusBarsPadding()
                    .clickable(onClick = { pressOnBack() })
            )
        }
    }
}

