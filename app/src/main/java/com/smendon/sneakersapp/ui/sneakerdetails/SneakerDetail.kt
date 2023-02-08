package com.smendon.sneakersapp.ui.sneakerdetails

import android.app.Activity
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Share
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
import io.branch.indexing.BranchUniversalObject
import io.branch.referral.Branch
import io.branch.referral.BranchError
import io.branch.referral.SharingHelper
import io.branch.referral.util.*
import org.json.JSONException
import org.json.JSONObject


@Composable
fun SneakerDetail(
    sneakerId: Int, sneakerDetailViewModel: SneakerDetailViewModel, pressOnBack: () -> Unit = {}
) {

    sneakerDetailViewModel.loadSneakerById(sneakerId)
    val sneaker: Sneaker? by sneakerDetailViewModel.sneakerLiveData.observeAsState()
    val mContext = LocalContext.current
    sneaker?.let { it ->
        SneakerDetailsBody(
            it, addToCart = {
                sneakerDetailViewModel.addToCart(it)
                Toast.makeText(mContext, "${it.name} added to cart", Toast.LENGTH_SHORT).show()
            },
            shareProductLink = {
                val buo = BranchUniversalObject().setCanonicalIdentifier("content/${it.id}")
                    .setTitle("Check out these sneakers")
                    .setContentDescription("These Nike sneakers are awesome")
                    .setContentImageUrl(it.image)
                    .setContentIndexingMode(BranchUniversalObject.CONTENT_INDEX_MODE.PUBLIC)
                    .setLocalIndexMode(BranchUniversalObject.CONTENT_INDEX_MODE.PUBLIC)
                val lp =
                    LinkProperties().setChannel("app").setFeature("sharing").setCampaign("sneakers")
                        .setStage("new user")
                        .addControlParameter("android_deeplink_path", it.id.toString())
                        .addControlParameter("id", it.id.toString())

                val ss = ShareSheetStyle(
                    mContext, "Check out these sneakers", "These Nike sneakers are awesome"
                ).setCopyUrlStyle(null, "Copy", "Added to clipboard")
                    .setMoreOptionStyle(null, "Show more")
                    .addPreferredSharingOption(SharingHelper.SHARE_WITH.WHATS_APP)
                    .addPreferredSharingOption(SharingHelper.SHARE_WITH.MESSAGE)
                    .addPreferredSharingOption(SharingHelper.SHARE_WITH.INSTAGRAM)
                    .addPreferredSharingOption(SharingHelper.SHARE_WITH.TWITTER)
                    .addPreferredSharingOption(SharingHelper.SHARE_WITH.GMAIL)
                    .setAsFullWidthStyle(true)
                    .setSharingTitle("Share With")

                buo.showShareSheet(mContext as Activity,
                    lp,
                    ss,
                    object : Branch.BranchLinkShareListener {
                        override fun onShareLinkDialogLaunched() {}
                        override fun onShareLinkDialogDismissed() {}
                        override fun onLinkShareResponse(
                            sharedLink: String?, sharedChannel: String?, error: BranchError?
                        ) {
                            val jsonObject = JSONObject()
                            try {
                                jsonObject.put("product", it.name)
                                jsonObject.put("price", it.price)
                            } catch (_: JSONException) {
                            }
                            Branch.getInstance().userCompletedAction("Product shared", jsonObject)
                        }

                        override fun onChannelSelected(channelName: String) {
                            val selectedSneaker = Product()
                            selectedSneaker.price = it.price.toDouble()
                            selectedSneaker.quantity = 1
                            selectedSneaker.brand = "Nike"
                            selectedSneaker.category = ProductCategory.APPAREL_AND_ACCESSORIES
                            selectedSneaker.name = it.name
                            selectedSneaker.sku = it.name
                            val commerceEvent = CommerceEvent()
                            commerceEvent.revenue = it.price.toDouble()
                            commerceEvent.currencyType = CurrencyType.INR
                            commerceEvent.addProduct(selectedSneaker)
                            Branch.getInstance().sendCommerceEvent(commerceEvent, null, null)
                        }
                    })
            }, pressOnBack
        )
    }
}

@Composable
private fun SneakerDetailsBody(
    sneaker: Sneaker,
    addToCart: (Sneaker) -> Unit,
    shareProductLink: () -> Unit,
    pressOnBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .background(MaterialTheme.colors.background)
            .fillMaxHeight()
    ) {

        ConstraintLayout {
            val (arrow, image, title, size, color, labelprice, price, addBtn, shareBtn) = createRefs()

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

            Text(text = sneaker.name,
                style = MaterialTheme.typography.h1,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                modifier = Modifier
                    .constrainAs(title) {
                        top.linkTo(image.bottom)
                    }
                    .padding(start = 16.dp, top = 30.dp, bottom = 30.dp))

            Text(text = "Size: 8",
                style = MaterialTheme.typography.h3,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                modifier = Modifier
                    .constrainAs(size) {
                        top.linkTo(title.bottom)
                    }
                    .padding(start = 16.dp, top = 30.dp, bottom = 30.dp))

            Text(text = "Color: White",
                style = MaterialTheme.typography.h3,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                modifier = Modifier
                    .constrainAs(color) {
                        top.linkTo(size.bottom)
                    }
                    .padding(start = 16.dp, top = 30.dp, bottom = 30.dp))

            Text(text = stringResource(R.string.label_price),
                style = MaterialTheme.typography.h3,
                modifier = Modifier
                    .constrainAs(labelprice) {
                        top.linkTo(color.bottom)
                        start.linkTo(parent.start)
                    }
                    .padding(start = 16.dp, top = 30.dp, bottom = 30.dp))

            Text(text = "₹${sneaker.price}",
                style = MaterialTheme.typography.body1,
                modifier = Modifier
                    .constrainAs(price) {
                        top.linkTo(color.bottom)
                        start.linkTo(labelprice.end)
                    }
                    .padding(start = 16.dp, top = 30.dp, bottom = 30.dp))

            Button(onClick = {
                addToCart(sneaker)
            },
                colors = ButtonDefaults.buttonColors(backgroundColor = Salmon),
                modifier = Modifier
                    .constrainAs(addBtn) {
                        top.linkTo(color.bottom)
                        end.linkTo(parent.end)
                    }
                    .padding(bottom = 30.dp, end = 16.dp, top = 16.dp)
            ) {
                Text(text = stringResource(R.string.label_add_to_cart), color = Color.White)
            }

            Icon(imageVector = Icons.Filled.ArrowBack,
                tint = Salmon,
                contentDescription = "Back",
                modifier = Modifier
                    .constrainAs(arrow) {
                        top.linkTo(parent.top)
                    }
                    .padding(12.dp)
                    .statusBarsPadding()
                    .clickable(onClick = { pressOnBack() })
            )

            Icon(imageVector = Icons.Filled.Share,
                tint = Salmon,
                contentDescription = "Share button",
                modifier = Modifier
                    .constrainAs(shareBtn) {
                        top.linkTo(parent.top)
                        end.linkTo(parent.end)
                    }
                    .padding(12.dp)
                    .statusBarsPadding()
                    .clickable(onClick = { shareProductLink() })
            )
        }
    }
}