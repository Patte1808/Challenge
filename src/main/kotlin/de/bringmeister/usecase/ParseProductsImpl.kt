package de.bringmeister.usecase

import com.fasterxml.jackson.dataformat.xml.XmlMapper
import com.fasterxml.jackson.dataformat.xml.annotation.*
import com.fasterxml.jackson.module.kotlin.KotlinModule
import de.bringmeister.domain.entity.Price
import de.bringmeister.domain.entity.ProductDetail
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

private data class ProductsXml(@JacksonXmlElementWrapper(useWrapping = false)
                               @JacksonXmlProperty(localName = "Product") val products: List<ProductXml>)

@JacksonXmlRootElement(localName = "Product")
private data class ProductXml(@JacksonXmlProperty(isAttribute = true) val id: String = "",
                              @JacksonXmlProperty(localName = "Name") val name: String,
                              @JacksonXmlProperty(localName = "Description") @JacksonXmlCData val description: String,
                              @JacksonXmlProperty val sku: String = "") {
    fun toProductDetail(prices: List<Price>) = ProductDetail(id = id, description = description, sku = sku,
            name = name, prices = prices)
}

@Component
class ParseProductsImpl : ParseProducts {

    @Autowired
    lateinit var parsePrices: ParsePrices

    override fun execute(): List<ProductDetail> {
        val objectMapper = XmlMapper().registerModule(KotlinModule())
        val productsXml = objectMapper
                .readValue(javaClass.getResourceAsStream("/products/products.xml"), ProductsXml::class.java)
        val prices = parsePrices.execute()

        return productsXml.products.map { productXml ->
            val productPrices = prices.filter { price -> price.sku == productXml.sku }

            productXml.toProductDetail(productPrices)
        }
    }
}