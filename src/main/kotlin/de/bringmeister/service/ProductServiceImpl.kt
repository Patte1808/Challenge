package de.bringmeister.service

import de.bringmeister.domain.repository.ProductRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ProductServiceImpl : ProductService {

    @Autowired
    lateinit var productRepository: ProductRepository

    override fun findAll() = productRepository.getAllProducts()

    override fun findById(id: String) = productRepository.getProductById(id)

    override fun findByIdWithUnit(id: String, unit: String) = productRepository.getProductByIdWithUnit(id, unit)
}