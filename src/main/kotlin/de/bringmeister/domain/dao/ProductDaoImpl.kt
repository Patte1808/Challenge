package de.bringmeister.domain.dao

import de.bringmeister.domain.entity.Product
import de.bringmeister.domain.entity.ProductDetail
import de.bringmeister.usecase.ParseProducts
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct

@Component
class ProductDaoImpl @Autowired constructor(private val parseProducts: ParseProducts) : ProductDao {

    private lateinit var productDetails: List<ProductDetail>

    @PostConstruct
    fun fetchProducts() {
        productDetails = parseProducts.execute()
    }

    override fun findProductById(id: String): ProductDetail? = productDetails.firstOrNull { product -> product.id == id }

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