package ru.timmson.feeder.stock.dao

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import ru.timmson.feeder.stock.model.Stock
import java.math.BigDecimal
import java.math.RoundingMode

@ExtendWith(MockitoExtension::class)
class MoscowExchangeDAOImplShould {

    private lateinit var moscowExchangeDAO: MoscowExchangeDAOImpl

    @Mock
    private lateinit var requester: Requester

    @BeforeEach
    fun setUp() {
        moscowExchangeDAO = MoscowExchangeDAOImpl(requester)
    }


    @Test
    fun returnUSDPrice() {
        val expected = Stock("usd", BigDecimal(74.86).setScale(2, RoundingMode.HALF_UP))
        val response = this.javaClass.classLoader.getResource("usd.json")?.readText()

        val url = "https://iss.moex.com/iss/engines/currency/markets/selt/securities/USD000UTSTOM.json"
        `when`(requester.url(url)).thenReturn(response)
        val actual = moscowExchangeDAO.getStockByTicker("usd")

        assertEquals(expected, actual)
    }

    /*@Test
    fun returnMoscowIndexPrice() {
        val expected = Stock("imoex", BigDecimal(74.86).setScale(2, RoundingMode.HALF_UP))
        val response = this.javaClass.classLoader.getResource("imoex.json")?.readText()

        val url = "https://iss.moex.com/iss/engines/stock/markets/index/securities/IMOEX.json"
        `when`(requester.url(url)).thenReturn(response)
        val actual = moscowExchangeDAO.getStockByTicker("imoex")

        assertEquals(expected, actual)
    }

    @Test
    fun returnMoscowRealtyIndexPrice() {
        val expected = Stock("mredc", BigDecimal(74.86).setScale(2, RoundingMode.HALF_UP))
        val response = this.javaClass.classLoader.getResource("mredc.json")?.readText()

        val url = "https://iss.moex.com/iss/engines/stock/markets/index/securities/MREDC.json"
        `when`(requester.url(url)).thenReturn(response)
        val actual = moscowExchangeDAO.getStockByTicker("mrdec")

        assertEquals(expected, actual)
    }*/

    @Test
    fun handleException() {
        `when`(requester.url("https://www.marketwatch.com/investing/index/spx1")).thenReturn("")

        assertThrows<StockDAOException> { moscowExchangeDAO.getStockByTicker("spx1") }
    }
}

