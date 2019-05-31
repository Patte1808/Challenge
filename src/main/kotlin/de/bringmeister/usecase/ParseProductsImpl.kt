package de.bringmeister.usecase

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.xml.annotation.*
import de.bringmeister.domain.entity.Price
import de.bringmeister.domain.entity.ProductDetail
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

internal data class ProductsXml(@JacksonXmlElementWrapper(useWrapping = false)
                               @JacksonXmlProperty(localName = "Product") val products: List<ProductXml>)

@JacksonXmlRootElement(localName = "Product")
internal data class ProductXml(@JacksonXmlProperty(isAttribute = true) val id: String = "",
                              @JacksonXmlProperty(localName = "Name") val name: String,
                              @JacksonXmlProperty(localName = "Description") @JacksonXmlCData val description: String,
                              @JacksonXmlProperty val sku: String = "") {
    fun toProductDetail(prices: List<Price>) = ProductDetail(id = id, description = description, sku = sku,
            name = name, prices = prices)
}

@Service
class ParseProductsImpl @Autowired constructor(private val parsePrices: ParsePrices, private val objectMapper: ObjectMapper)
    : ParseProducts {

    override fun execute(): List<ProductDetail> {
        val productsXml = objectMapper
                .readValue(javaClass.getResourceAsStream("/products/products.xml"), ProductsXml::class.java)
        val prices = parsePrices.execute()

        return productsXml.products.map { productXml ->
            val productPrices = prices.filter { price -> price.sku == productXml.sku }

            productXml.toProductDetail(productPrices)
        }
    }
}