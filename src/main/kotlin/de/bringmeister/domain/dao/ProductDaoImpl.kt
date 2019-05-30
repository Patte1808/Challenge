package de.bringmeister.domain.dao

import de.bringmeister.domain.entity.Product
import de.bringmeister.domain.entity.ProductDetail
import de.bringmeister.usecase.ParseProducts
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct

@Component
class ProductDaoImpl : ProductDao {

    @Autowired
    lateinit var parseProducts: ParseProducts

    private lateinit var productDetails: List<ProductDetail>

    @PostConstruct
    fun init() {
        productDetails = parseProducts.execute()
    }

    override fun findProductById(id: String): ProductDetail? = productDetails.firstOrNull { product -> product.sku == id }

    override fun findProductByIdWithUnit(id: String, unit: String): ProductDetail? {
        val product = findProductById(id)

        product?.let {
            val price = it.prices.filter { price -> price.unit == unit }

            return product.copy(prices = price)
        }

        return null
    }

    override fun findAllProducts(): List<Product> = productDetails.map { productDetail -> productDetail.toProduct() }
}