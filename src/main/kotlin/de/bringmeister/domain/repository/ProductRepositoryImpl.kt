package de.bringmeister.domain.repository

import de.bringmeister.domain.dao.ProductDao
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class ProductRepositoryImpl @Autowired constructor(private val productDao: ProductDao) : ProductRepository {

    /**
     * Get all products available in our data source
     */
    override fun getAllProducts() = productDao.findAllProducts()

    /**
     * Get product by specific ID
     * Note: this ID is not the SKU
     */
    override fun getProductById(id: String) = productDao.findProductById(id)

    /**
     * Get product by specific ID and unit.
     * If unit is not present in product, it will display the product without any price
     * Note: this ID is not the SKU
     */
    override fun getProductByIdWithUnit(id: String, unit: String) = productDao.findProductByIdWithUnit(id, unit)
}