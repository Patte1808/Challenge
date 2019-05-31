package de.bringmeister.domain.repository

import de.bringmeister.domain.dao.ProductDao
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class ProductRepositoryImpl @Autowired constructor(private val productDao: ProductDao) : ProductRepository {

    override fun getAllProducts() = productDao.findAllProducts()

    override fun getProductById(id: String) = productDao.findProductById(id)

    override fun getProductByIdWithUnit(id: String, unit: String) = productDao.findProductByIdWithUnit(id, unit)
}