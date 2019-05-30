package de.bringmeister.controller

import de.bringmeister.domain.entity.ProductDetail
import de.bringmeister.service.ProductService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("products")
class ProductController {

    @Autowired
    lateinit var productService: ProductService

    @GetMapping("/", produces = ["application/json"])
    fun getAllProducts() = productService.findAll()

    @GetMapping("/{id}", produces = ["application/json"])
    fun getProductById(@PathVariable id: String, @RequestParam("unit", required = false) unit: String?): ProductDetail? {
        unit?.let { unit ->
            return productService.findByIdWithUnit(id, unit)
        }

        return productService.findById(id)
    }
}