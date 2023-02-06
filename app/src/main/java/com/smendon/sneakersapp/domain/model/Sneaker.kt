package com.smendon.sneakersapp.domain.model

data class Sneaker(
    val id: Int,
    val name: String,
    val price: String,
    val currencyCode: String,
    val image: String
) {
    fun toCartItem(): CartItem {
        return CartItem(
            id = this.id,
            name = this.name,
            price = this.price.toDouble(),
            currencyCode = this.currencyCode,
            image = this.image,
            quantity = 1
        )
    }

    companion object {
        fun mock() = Sneaker(
            id = 0,
            name = "Nike Air Jordan",
            price = "199",
            currencyCode = "INR",
            image = "https://media.istockphoto.com/id/1320501530/es/foto/zapatillas-blancas-sobre-fondo-degradado-azul-moda-masculina-calzado-deportivo-zapatillas.jpg?s=612x612&w=is&k=20&c=2XULHllc4ihBTFFAn99edEikffU2QtB-T0bayQsK5Ys="
        )
    }
}
