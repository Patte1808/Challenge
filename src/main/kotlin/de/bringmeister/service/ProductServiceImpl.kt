package de.bringmeister.service

import de.bringmeister.domain.repository.ProductRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ProductServiceImpl @Autowired constructor(private val productRepository: ProductRepository) : ProductService {
    
    override fun findAll() = productRepository.getAllProducts()

    override fun findById(id: String) = productRepository.getProductById(id)

    override fun findByIdWithUnit(id: String, unit: String) = productRepository.getProductByIdWithUnit(id, unit)
}