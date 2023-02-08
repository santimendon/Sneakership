package com.smendon.sneakersapp.branch

import android.app.Application
import com.smendon.sneakersapp.domain.model.CartItem
import com.smendon.sneakersapp.domain.model.Sneaker
import io.branch.indexing.BranchUniversalObject
import io.branch.referral.util.*
import javax.inject.Inject

class BranchHelper @Inject constructor(private val application: Application) {

    fun tagViewItem(product: Sneaker) {
        val buo = BranchUniversalObject()
            .setTitle("View Item")
            .setContentMetadata(
                ContentMetadata()
                    .setPrice(product.price.toDouble(), CurrencyType.INR)
                    .setProductBrand("Nike")
                    .setProductCategory(ProductCategory.APPAREL_AND_ACCESSORIES)
                    .setProductName(product.name)
                    .setContentSchema(BranchContentSchema.COMMERCE_PRODUCT)
            )
        BranchEvent(BRANCH_STANDARD_EVENT.VIEW_ITEM)
            .setDescription("Customer viewed product")
            .addContentItems(buo)
            .logEvent(application)
    }

    fun tagAddToCart(product: CartItem) {
        val buo = BranchUniversalObject()
            .setCanonicalIdentifier("sneaker/${product.id}")
            .setTitle("Add to Cart")
            .setContentMetadata(
                ContentMetadata()
                    .setPrice(product.price, CurrencyType.INR)
                    .setProductBrand("Nike")
                    .setProductCategory(ProductCategory.APPAREL_AND_ACCESSORIES)
                    .setProductName(product.name)
                    .setQuantity(1.0)
                    .setContentSchema(BranchContentSchema.COMMERCE_PRODUCT)
            )
        BranchEvent(BRANCH_STANDARD_EVENT.ADD_TO_CART)
            .setCurrency(CurrencyType.INR)
            .setDescription("Customer added item to cart")
            .setRevenue(product.price)
            .addContentItems(buo)
            .logEvent(application)
    }

    fun tagDeleteFromCart(product: CartItem) {
        val buo = BranchUniversalObject()
            .setTitle("Remove Item From Cart")
            .setContentMetadata(
                ContentMetadata()
                    .setPrice(product.price, CurrencyType.INR)
                    .setProductBrand("Nike")
                    .setProductCategory(ProductCategory.APPAREL_AND_ACCESSORIES)
                    .setProductName(product.name)
                    .setQuantity(1.0)
                    .setContentSchema(BranchContentSchema.COMMERCE_PRODUCT)
            )
        BranchEvent("Remove Item From Cart")
            .addCustomDataProperty("Cart Item Name", product.name)
            .addCustomDataProperty("Item Id", product.id.toString())
            .setCurrency(CurrencyType.INR)
            .setDescription("Customer removed item from cart")
            .addContentItems(buo)
            .logEvent(application)
    }

    fun tagCartCheckout(cartItems: List<CartItem>, totalCartValue: Double) {
        val quantity = cartItems.count()
        val buo = BranchUniversalObject()
            .setTitle("Cart Checkout")
            .setContentMetadata(
                ContentMetadata()
                    .setPrice(totalCartValue, CurrencyType.INR)
                    .setProductBrand("Nike")
                    .setProductCategory(ProductCategory.APPAREL_AND_ACCESSORIES)
                    .setQuantity(quantity.toDouble())
                    .setContentSchema(BranchContentSchema.COMMERCE_PRODUCT)
            )
        BranchEvent(BRANCH_STANDARD_EVENT.PURCHASE)
            .setCurrency(CurrencyType.INR)
            .setDescription("Customer initiated payment")
            .setRevenue(totalCartValue)
            .addContentItems(buo)
            .logEvent(application)
    }

}