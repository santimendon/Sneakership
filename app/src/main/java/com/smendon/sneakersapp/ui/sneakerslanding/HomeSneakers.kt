package com.smendon.sneakersapp.ui.sneakerslanding

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.AsyncImage
import com.smendon.sneakersapp.domain.model.Sneaker
import com.smendon.sneakersapp.ui.theme.SneakersAppTheme

@Composable
fun HomeSneakers(
    modifier: Modifier = Modifier,
    sneakers: List<Sneaker>,
    selectSneaker: (Int) -> Unit,
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.padding(4.dp)
    ) {
        items(sneakers) { sneaker ->
            key(sneaker.id) {
                HomeSneaker(
                    sneaker = sneaker, selectSneaker = selectSneaker
                )
            }
        }
    }
}

@Composable
private fun HomeSneaker(
    modifier: Modifier = Modifier,
    sneaker: Sneaker,
    selectSneaker: (Int) -> Unit = {},
) {
    Surface(
        modifier = modifier
            .padding(4.dp)
            .clickable(onClick = { selectSneaker(sneaker.id) }),
        color = MaterialTheme.colors.onBackground,
        elevation = 20.dp,
        shape = RoundedCornerShape(25.dp)
    ) {
        ConstraintLayout() {
            val (image, title, content) = createRefs()
            AsyncImage(model = sneaker.image,
                contentDescription = sneaker.name,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .aspectRatio(1.0f)
                    .constrainAs(image) {
                        centerHorizontallyTo(parent)
                        top.linkTo(parent.top)
                    })

            Text(
                modifier = Modifier
                    .constrainAs(title) {
                        centerHorizontallyTo(parent)
                        top.linkTo(image.bottom)
                    }
                    .padding(8.dp),
                text = sneaker.name,
                style = MaterialTheme.typography.h2,
                textAlign = TextAlign.Center,
            )

            Text(
                modifier = Modifier
                    .constrainAs(content) {
                        centerHorizontallyTo(parent)
                        top.linkTo(title.bottom)
                    }
                    .padding(horizontal = 8.dp)
                    .padding(bottom = 12.dp),
                text = "\u20B9${sneaker.price}",
                style = MaterialTheme.typography.body1,
                textAlign = TextAlign.Center,
            )
        }
    }
}

@Composable
@Preview(name = "HomeSneaker Light Theme")
private fun HomeSneakerPreviewLight() {
    SneakersAppTheme(darkTheme = false) {
        HomeSneaker(
            sneaker = Sneaker.mock()
        )
    }
}

@Composable
@Preview(name = "HomeSneaker Dark Theme")
private fun HomeSneakerPreviewDark() {
    SneakersAppTheme(darkTheme = true) {
        HomeSneaker(
            sneaker = Sneaker.mock()
        )
    }
}