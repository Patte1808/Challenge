package de.bringmeister.controller

import de.bringmeister.domain.entity.Price
import de.bringmeister.domain.entity.Product
import de.bringmeister.domain.entity.ProductDetail
import de.bringmeister.service.ProductService
import org.amshove.kluent.When
import org.amshove.kluent.calling
import org.amshove.kluent.itReturns
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import kotlin.test.assertEquals
import kotlin.test.assertNull

@RunWith(MockitoJUnitRunner::class)
class ProductControllerTest {

    @InjectMocks
    lateinit var productController: ProductController

    @Mock
    lateinit var productService: ProductService

    private val mockProductDetails = listOf(
            ProductDetail("1", "Test Product 1", "Lorem Ipsum", "11", listOf(Price("1", 5.55, "EUR", "PACKAGE")))
    )

    private val mockProducts = listOf(
            Product("1", "Test Product 1", "Lorem Ipsum", "11")
    )

    @Before
    fun init() {
        When calling productService.findAll() itReturns mockProducts

        When calling productService.findById("0") itReturns null

        When calling productService.findById("1") itReturns mockProductDetails[0]

        When calling productService.findByIdWithUnit("1", "PACKAGE") itReturns mockProductDetails[0]

        When calling productService.findByIdWithUnit("1", "SOME OTHER PACKAGE") itReturns mockProductDetails[0].copy(prices = emptyList())
    }

    @Test
    fun `Should return all products when calling getAllProducts`() {
        val products = productController.getAllProducts()

        assertEquals(mockProducts, products)
    }

    @Test
    fun `Should return empty list when calling getAllProducts but products null`() {
        When calling productService.findAll() itReturns emptyList()

        val products = productController.getAllProducts()

        assertEquals(emptyList(), products)
    }

    @Test
    fun `Should return null when calling getProductById and id is not present in product list`() {
        val product = productController.getProductById("0").body

        assertNull(product)
    }

    @Test
    fun `Should return product when calling getProductById`() {
        val product = productController.getProductById("1").body

        assertEquals(mockProductDetails[0], product)
    }

    @Test
    fun `Should return product when calling getProductById with specified unit`() {
        val product = productController.getProductById("1", "PACKAGE").body

        assertEquals(mockProductDetails[0], product)
    }

    @Test
    fun `Should return product without price when calling getProductById with specified unit not present in list`() {
        val product = productController.getProductById("1", "SOME OTHER PACKAGE").body

        assertEquals(mockProductDetails[0].id, product?.id)
        assertEquals(mockProductDetails[0].sku, product?.sku)
        assertEquals(mockProductDetails[0].name, product?.name)
        assertEquals(mockProductDetails[0].description, product?.description)
        assertEquals(0, product?.prices?.size)
    }
}