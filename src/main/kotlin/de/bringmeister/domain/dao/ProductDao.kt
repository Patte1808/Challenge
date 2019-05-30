package de.bringmeister.domain.dao

import de.bringmeister.domain.entity.Product
import de.bringmeister.domain.entity.ProductDetail

interface ProductDao {
    fun findProductById(id: String): ProductDetail?

    fun findProductByIdWithUnit(id: String, unit: String): ProductDetail?

    fun findAllProducts(): List<Product>
}