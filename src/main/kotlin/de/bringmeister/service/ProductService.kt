package de.bringmeister.service

import de.bringmeister.domain.entity.Product
import de.bringmeister.domain.entity.ProductDetail

interface ProductService {
    fun findAll(): List<Product>

    fun findById(id: String): ProductDetail?

    fun findByIdWithUnit(id: String, unit: String): ProductDetail?
}