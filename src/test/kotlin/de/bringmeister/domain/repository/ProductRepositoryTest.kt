package de.bringmeister.domain.repository

import com.nhaarman.mockitokotlin2.eq
import de.bringmeister.domain.dao.ProductDao
import de.bringmeister.domain.entity.Price
import de.bringmeister.domain.entity.Product
import de.bringmeister.domain.entity.ProductDetail
import org.amshove.kluent.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class ProductRepositoryTest {
    private val productDao = Mockito.mock(ProductDao::class.java)
    lateinit var productRepository: ProductRepository

    private val mockProductDetails = listOf(
            ProductDetail("1", "Test Product 1", "Lorem Ipsum", "11", listOf(Price("1", 5.55, "EUR", "PACKAGE")))
    )

    private val mockProducts = listOf(
            Product("1", "Test Product 1", "Lorem Ipsum", "11")
    )

    @BeforeEach
    fun init() {
        productRepository = ProductRepositoryImpl(productDao)

        When calling productDao.findAllProducts() itReturns mockProducts
        When calling productDao.findProductById(eq("0")) itReturns null
        When calling productDao.findProductById(eq("1")) itReturns mockProductDetails[0]
    }

    @Test
    fun `Should call findAllProducts when calling getAllProducts`() {
        productRepository.getAllProducts()

        Verify on productDao that productRepository.getAllProducts() was called
    }

    @Test
    fun `Should be the same product from DAO when calling getAllProducts`() {
        val products = productRepository.getAllProducts()

        assertEquals(mockProductDetails[0].id, products[0].id)
        assertEquals(mockProductDetails[0].description, products[0].description)
        assertEquals(mockProductDetails[0].name, products[0].name)
        assertEquals(mockProductDetails[0].sku, products[0].sku)
    }

    @Test
    fun `Should get 1 product when calling getAllProduct`() {
        val products = productRepository.getAllProducts()

        assertEquals(mockProductDetails.size, products.size)
    }

    @Test
    fun `Should call findProductById when calling getProductById`() {
        productRepository.getProductById("0")

        Verify on productDao that productDao.findProductById(eq("0")) was called
    }

    @Test
    fun `Should get null when calling getProductById with product which does not exist`() {
        val product = productRepository.getProductById("0")

        assertNull(product)
    }

    @Test
    fun `Should get a product when calling getProductById with product which exists`() {
        val product = productRepository.getProductById("1")

        assertNotNull(product)
    }

    @Test
    fun `Should be the same product when calling getProductById with product which exists`() {
        val product = productRepository.getProductById("1")

        assertEquals(mockProductDetails[0].id, product?.id)
        assertEquals(mockProductDetails[0].name, product?.name)
        assertEquals(mockProductDetails[0].description, product?.description)
        assertEquals(mockProductDetails[0].sku, product?.sku)
        assertEquals(mockProductDetails[0].prices, product?.prices)
    }
}