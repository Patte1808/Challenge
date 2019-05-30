package de.bringmeister.domain.repository

import de.bringmeister.domain.entity.Product
import de.bringmeister.domain.entity.ProductDetail

interface ProductRepository {
    fun getAllProducts(): List<Product>

    fun getProductById(id: String): ProductDetail?

    fun getProductByIdWithUnit(id: String, unit: String): ProductDetail?
}