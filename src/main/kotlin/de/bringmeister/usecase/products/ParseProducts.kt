package de.bringmeister.usecase.products

import de.bringmeister.domain.entity.ProductDetail

interface ParseProducts {
    fun execute(): List<ProductDetail>
}