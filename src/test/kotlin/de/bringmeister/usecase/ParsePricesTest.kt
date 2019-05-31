package de.bringmeister.usecase

import com.beust.klaxon.Klaxon
import org.amshove.kluent.When
import org.amshove.kluent.any
import org.amshove.kluent.calling
import org.amshove.kluent.itReturns
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import java.io.InputStream
import kotlin.test.assertTrue

class ParsePricesTest {
    /*private val klaxon = Mockito.mock(Klaxon::class.java)
    private lateinit var parsePrices: ParsePrices

    @BeforeEach
    fun init() {
        parsePrices = ParsePricesImpl(klaxon)

        When calling klaxon.parseArray<PriceJson>(any<InputStream>()) itReturns emptyList()
    }

    @Test
    fun `Should return emptyList when calling execute`() {
        val prices = parsePrices.execute()

        assertTrue(prices.isEmpty())
    }*/
}