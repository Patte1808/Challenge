package de.bringmeister.usecase

import de.bringmeister.domain.entity.Price

interface ParsePrices {
    fun execute(): List<Price>
}