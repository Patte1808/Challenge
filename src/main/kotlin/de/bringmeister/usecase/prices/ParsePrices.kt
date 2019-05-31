package de.bringmeister.usecase.prices

import de.bringmeister.domain.entity.Price

interface ParsePrices {
    fun execute(): List<Price>
}