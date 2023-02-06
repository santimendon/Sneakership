package com.smendon.sneakersapp.data.datasource.remote

import com.smendon.sneakersapp.domain.model.Sneaker

class RemoteData {

    private val sneakersList = listOf(
        Sneaker(
            id = 0,
            name = "Nike Air Jordan 1",
            price = "199",
            currencyCode = "INR",
            image = "https://images.unsplash.com/photo-1580906853149-f82f7601d205?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1974&q=80"
        ),
        Sneaker(
            id = 1,
            name = "Nike Air Jordan 2",
            price = "199",
            currencyCode = "INR",
            image = "https://images.unsplash.com/photo-1514989940723-e8e51635b782?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=2070&q=80"
        ),
        Sneaker(
            id = 2,
            name = "Nike Air Jordan 3",
            price = "199",
            currencyCode = "INR",
            image = "https://images.unsplash.com/photo-1600181516264-3ea807ff44b9?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=987&q=80"
        ),
        Sneaker(
            id = 3,
            name = "Nike Air Jordan 4",
            price = "199",
            currencyCode = "INR",
            image = "https://images.unsplash.com/photo-1600185365483-26d7a4cc7519?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1625&q=80"
        ),
        Sneaker(
            id = 4,
            name = "Nike Air Jordan 5",
            price = "199",
            currencyCode = "INR",
            image = "https://images.unsplash.com/photo-1543508282-6319a3e2621f?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1015&q=80"
        ),
        Sneaker(
            id = 5,
            name = "Nike Air Jordan 6",
            price = "199",
            currencyCode = "INR",
            image = "https://images.unsplash.com/photo-1580894529573-fc5e3f78ba06?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1974&q=80"
        ),
        Sneaker(
            id = 6,
            name = "Nike Air Jordan 7",
            price = "199",
            currencyCode = "INR",
            image = "https://images.unsplash.com/photo-1521093470119-a3acdc43374a?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=987&q=80"
        ),
        Sneaker(
            id = 7,
            name = "Nike Air Jordan 8",
            price = "199",
            currencyCode = "INR",
            image = "https://images.unsplash.com/photo-1539185441755-769473a23570?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=2071&q=80"
        ),
        Sneaker(
            id = 8,
            name = "Nike Air Jordan 9",
            price = "199",
            currencyCode = "INR",
            image = "https://images.unsplash.com/photo-1595909109003-889f4ee04d2b?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=987&q=80"
        ),
        Sneaker(
            id = 9,
            name = "Nike Air Jordan 10",
            price = "199",
            currencyCode = "INR",
            image = "https://media.istockphoto.com/id/1320501530/es/foto/zapatillas-blancas-sobre-fondo-degradado-azul-moda-masculina-calzado-deportivo-zapatillas.jpg?s=612x612&w=is&k=20&c=2XULHllc4ihBTFFAn99edEikffU2QtB-T0bayQsK5Ys="
        ),
        Sneaker(
            id = 10,
            name = "Nike Air",
            price = "199",
            currencyCode = "INR",
            image = "https://images.unsplash.com/photo-1613489647684-4dc8c9948245?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxzZWFyY2h8MTZ8fG5pa2UlMjBzbmVha2VyfGVufDB8fDB8fA%3D%3D&auto=format&fit=crop&w=800&q=60"
        )
    )

    fun getAllSneakers(): List<Sneaker> = sneakersList

    fun getSneakerById(sneakerId: Int): Sneaker? {
        return sneakersList.find { sneaker -> sneaker.id.equals(sneakerId) }
    }
}