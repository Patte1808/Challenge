package de.bringmeister.controller

import de.bringmeister.domain.entity.Product
import de.bringmeister.domain.entity.ProductDetail
import de.bringmeister.service.ProductService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("products", produces = ["application/json"])
class ProductController @Autowired constructor(private val productService: ProductService) {

    @GetMapping("/")
    fun getAllProducts(): List<Product> = productService.findAll()

    @GetMapping("/{id}")
    fun getProductById(@PathVariable id: String, @RequestParam("unit", required = false) priceUnit: String? = null)
            : ResponseEntity<ProductDetail> {

        val product = when(priceUnit == null) {
            true -> productService.findById(id)
            false -> productService.findByIdWithUnit(id, priceUnit)
        }

        return when(product == null) {
            true -> ResponseEntity.notFound().build()
            false -> ResponseEntity.ok(product)
        }
    }
}