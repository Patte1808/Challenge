package de.bringmeister.usecase

import com.beust.klaxon.Klaxon
import de.bringmeister.domain.entity.Price
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

internal data class PriceJson(val id: String, val price: Map<String, Any>, val unit: String) {
    fun toPrice() = Price(sku = id, value = price["value"] as Double, currency = price["currency"] as String, unit = unit)
}

@Service
class ParsePricesImpl @Autowired constructor(private val klaxon: Klaxon) : ParsePrices {
    override fun execute(): List<Price> {
        val result = klaxon.parseArray<PriceJson>(javaClass.getResourceAsStream("/products/prices.json")) ?: emptyList()

        return result.map { priceJson -> priceJson.toPrice() }
    }
}