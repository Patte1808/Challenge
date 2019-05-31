package de.bringmeister.domain.dao

import de.bringmeister.domain.entity.Price
import de.bringmeister.domain.entity.ProductDetail
import de.bringmeister.usecase.ParseProducts
import org.amshove.kluent.When
import org.amshove.kluent.calling
import org.amshove.kluent.itReturns
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import kotlin.test.assertEquals
import kotlin.test.assertNull

class ProductDaoTest {
    private val parseProducts = Mockito.mock(ParseProducts::class.java)
    lateinit var productDao: ProductDao

    private val mockProductDetails = listOf(
            ProductDetail("1", "Test Product 1", "Lorem Ipsum", "11", listOf(Price("1", 5.55, "EUR", "PACKAGE")))
    )

    @BeforeEach
    fun init() {
        productDao = ProductDaoImpl(parseProducts)

        When calling parseProducts.execute() itReturns mockProductDetails

        (productDao as ProductDaoImpl).fetchProducts()
    }

    @Test
    fun `Should return null when calling findProductById with id not in product list`() {
        val product = productDao.findProductById("0")

        assertNull(product)
    }

    @Test
    fun `Should return correct product when calling findProductById`() {
        val product = productDao.findProductById("1")

        assertEquals(mockProductDetails[0], product)
    }

    @Test
    fun `Should return null when calling findProductByIdWithUnit with id not in product list`() {
        val product = productDao.findProductByIdWithUnit("0", "PACKAGE")

        assertNull(product)
    }

    @Test
    fun `Should return correct product with correct unit when calling findProductByIdWithUnit`() {
        val product = productDao.findProductByIdWithUnit("1", "PACKAGE")

        assertEquals(mockProductDetails[0].id, product?.id)
        assertEquals(mockProductDetails[0].name, product?.name)
        assertEquals(mockProductDetails[0].sku, product?.sku)
        assertEquals(mockProductDetails[0].description, product?.description)
        assertEquals(1, product?.prices?.size)
        assertEquals(mockProductDetails[0].prices, product?.prices)
    }

    @Test
    fun `Should return correct product without price if unit is not present in product when calling findProductByIdWithUnit`() {
        val product = productDao.findProductByIdWithUnit("1", "SOME OTHER UNIT")

        assertEquals(mockProductDetails[0].id, product?.id)
        assertEquals(mockProductDetails[0].name, product?.name)
        assertEquals(mockProductDetails[0].sku, product?.sku)
        assertEquals(mockProductDetails[0].description, product?.description)
        assertEquals(0, product?.prices?.size)
    }

    @Test
    fun `Should return all products when calling findAllProducts`() {
        val products = productDao.findAllProducts()

        assertEquals(1, products.size)
    }

    @Test
    fun `Should convert productdetail to product when calling findAllProducts`() {
        val products = productDao.findAllProducts()

        assertEquals(mockProductDetails[0].id, products[0].id)
        assertEquals(mockProductDetails[0].name, products[0].name)
        assertEquals(mockProductDetails[0].description, products[0].description)
        assertEquals(mockProductDetails[0].sku, products[0].sku)
    }
}