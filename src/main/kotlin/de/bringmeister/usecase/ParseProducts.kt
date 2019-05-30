package de.bringmeister.usecase

import de.bringmeister.domain.entity.ProductDetail

interface ParseProducts {
    fun execute(): List<ProductDetail>
}