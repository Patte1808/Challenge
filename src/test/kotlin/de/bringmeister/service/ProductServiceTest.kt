package de.bringmeister.service

import com.nhaarman.mockitokotlin2.eq
import de.bringmeister.domain.entity.Price
import de.bringmeister.domain.entity.Product
import de.bringmeister.domain.entity.ProductDetail
import de.bringmeister.domain.repository.ProductRepository
import org.amshove.kluent.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import kotlin.test.assertEquals
import kotlin.test.assertNull

class ProductServiceTest {
    private val productRepository = Mockito.mock(ProductRepository::class.java)
    private lateinit var productService: ProductService

    private val mockProductDetails = listOf(
            ProductDetail("1", "Test Product 1", "Lorem Ipsum", "11", listOf(Price("1", 5.55, "EUR", "PACKAGE")))
    )

    private val mockProducts = listOf(
            Product("1", "Test Product 1", "Lorem Ipsum", "11")
    )

    @BeforeEach
    fun init() {
        productService = ProductServiceImpl(productRepository)

        When calling productRepository.getAllProducts() itReturns mockProducts
        When calling productRepository.getProductById(eq("0")) itReturns null
        When calling productRepository.getProductById(eq("1")) itReturns mockProductDetails[0]
        When calling productRepository.getProductByIdWithUnit(eq("0"), eq("PACKAGE")) itReturns null
        When calling productRepository.getProductByIdWithUnit(eq("1"), eq("PACKAGE")) itReturns mockProductDetails[0]
        When calling productRepository.getProductByIdWithUnit(eq("1"), eq("SOME OTHER PACKAGE")) itReturns mockProductDetails[0].copy(prices = emptyList())
    }

    @Test
    fun `Should call getAllProducts when calling findAllProducts`() {
        productService.findAll()

        Verify on productRepository that productRepository.getAllProducts() was called
    }

    @Test
    fun `Should call getProductById when calling findProduct`() {
        productService.findById("0")

        Verify on productRepository that productRepository.getProductById(any()) was called
    }

    @Test
    fun `Should call getProductByIdWithUnit when calling findByIdWithUnit`() {
        productService.findByIdWithUnit("0", "PACKAGE")

        Verify on productRepository that productRepository.getProductByIdWithUnit(any(), any()) was called
    }

    @Test
    fun `Should return product list unchanged when calling findAllProducts`() {
        val products = productService.findAll()

        assertEquals(mockProducts.size, products.size)
        assertEquals(mockProducts, products)
    }

    @Test
    fun `Should return product unchanged when calling findById`() {
        val product = productService.findById("1")

        assertEquals(mockProductDetails[0], product)
    }

    @Test
    fun `Should return null when calling findById with id not in list`() {
        val product = productService.findById("0")

        assertNull(product)
    }

    @Test
    fun `Should return null when calling findByIdWithUnit with id not in list`() {
        val product = productService.findByIdWithUnit("0", "PACKAGE")

        assertNull(product)
    }

    @Test
    fun `Should return correct product when calling findByIdWithUnit`() {
        val product = productService.findByIdWithUnit("1", "PACKAGE")

        assertEquals(mockProductDetails[0], product)
    }

    @Test
    fun `Should return correct product with empty price list when calling findByIdWithUnit where unit is not in price list`() {
        val product = productService.findByIdWithUnit("1", "SOME OTHER PACKAGE")

        assertEquals(mockProductDetails[0].id, product?.id)
        assertEquals(mockProductDetails[0].name, product?.name)
        assertEquals(mockProductDetails[0].sku, product?.sku)
        assertEquals(mockProductDetails[0].description, product?.description)
        assertEquals(0, product?.prices?.size)
    }
}