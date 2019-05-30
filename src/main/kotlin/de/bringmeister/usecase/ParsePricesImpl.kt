package de.bringmeister.usecase

import com.beust.klaxon.Klaxon
import de.bringmeister.domain.entity.Price
import org.springframework.stereotype.Component

private data class PriceJson(val id: String, val price: Map<String, Object>, val unit: String) {
    fun toPrice() = Price(sku = id, value = price["value"] as Double, currency = price["currency"] as String, unit = unit)
}

@Component
class ParsePricesImpl : ParsePrices {
    override fun execute(): List<Price> {
        val result = Klaxon().parseArray<PriceJson>(javaClass.getResourceAsStream("/products/prices.json")) ?: emptyList()

        return result.map { priceJson -> priceJson.toPrice() }
    }
}