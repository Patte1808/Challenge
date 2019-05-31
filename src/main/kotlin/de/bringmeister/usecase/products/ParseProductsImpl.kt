package de.bringmeister.usecase.products

import com.fasterxml.jackson.dataformat.xml.annotation.*
import com.fasterxml.jackson.module.kotlin.KotlinModule
import de.bringmeister.domain.entity.Price
import de.bringmeister.domain.entity.ProductDetail
import de.bringmeister.usecase.prices.ParsePrices
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter
import org.springframework.stereotype.Service

/**
 * This class acts as a mapper for the JSON representation.
 * It should never be used outside of this class, therefore its private
 */
private data class ProductsXml(@JacksonXmlElementWrapper(useWrapping = false)
                               @JacksonXmlProperty(localName = "Product") val products: List<ProductXml>)

@JacksonXmlRootElement(localName = "Product")
internal data class ProductXml(@JacksonXmlProperty(isAttribute = true) val id: String = "",
                              @JacksonXmlProperty(localName = "Name") val name: String,
                              @JacksonXmlProperty(localName = "Description") @JacksonXmlCData val description: String,
                              @JacksonXmlProperty val sku: String = "") {
    fun toProductDetail(prices: List<Price>) = ProductDetail(id = id, description = description.trim(), sku = sku,
            name = name.trim(), prices = prices)
}

/**
 * Is responsible for parsing the prices json file and convert it to our domain object price
 */
@Service
class ParseProductsImpl @Autowired constructor(private val parsePrices: ParsePrices,
                                               private val converter: MappingJackson2XmlHttpMessageConverter) : ParseProducts {

    override fun execute(): List<ProductDetail> {
        val productsXml = converter.objectMapper.registerModule(KotlinModule())
                .readValue(javaClass.getResourceAsStream("/products/products.xml"), ProductsXml::class.java)
        val prices = parsePrices.execute()

        return productsXml.products.map { productXml ->
            val productPrices = prices.filter { price -> price.sku == productXml.sku }

            productXml.toProductDetail(productPrices)
        }
    }
}