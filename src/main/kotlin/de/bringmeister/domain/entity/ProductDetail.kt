package de.bringmeister.domain.entity

/*
Holds the joined data of Product and Price

As Product (without price) is a subset of this model, I also added a `toProduct` method to easily convert this into a product
*/
data class ProductDetail(val id: String, val name: String, val description: String, val sku: String, val prices: List<Price>) {
    fun toProduct() = Product(id, name, description, sku)
}