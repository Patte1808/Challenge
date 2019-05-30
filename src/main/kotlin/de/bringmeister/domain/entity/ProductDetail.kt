package de.bringmeister.domain.entity

data class ProductDetail(val id: String, val name: String, val description: String, val sku: String, val prices: List<Price>) {
    fun toProduct() = Product(id, name, description, sku)
}