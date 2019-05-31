package de.bringmeister.controller

import de.bringmeister.domain.entity.ProductDetail
import de.bringmeister.service.ProductService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("products")
class ProductController @Autowired constructor(private val productService: ProductService) {

    @GetMapping("/", produces = ["application/json"])
    fun getAllProducts() = productService.findAll()

    @GetMapping("/{id}", produces = ["application/json"])
    fun getProductById(@PathVariable id: String, @RequestParam("unit", required = false) priceUnit: String?): ProductDetail? {
        priceUnit?.let { priceUnit ->
            return productService.findByIdWithUnit(id, priceUnit)
        }

        return productService.findById(id)
    }
}