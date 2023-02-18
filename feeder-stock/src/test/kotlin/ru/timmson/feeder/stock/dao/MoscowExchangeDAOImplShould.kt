package ru.timmson.feeder.stock.dao

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
class MoscowExchangeDAOImplShould {

    private lateinit var moscowExchangeDAO: MoscowExchangeDAOImpl

    @Mock
    private lateinit var requester: Requester


    @BeforeEach
    fun setUp() {
        moscowExchangeDAO = MoscowExchangeDAOImpl(requester)
    }


    /*    @Test
        fun returnPriceUSD() {
            val expected = Stock("usd", BigDecimal(76.09).setScale(2, RoundingMode.HALF_UP))

            val response = MoscowExchangeDAOImplShould::class.java.getResource("usd.json")?.readText()

            `when`(requester.url("https://iss.moex.com/iss/engines/currency/markets/selt/securities.jsonp?securities=USD000UTSTOM")).thenReturn(response)
            val actual = moscowExchangeDAO.getStockByTicker("spx")

            assertEquals(expected, actual)
        }*/

    @Test
    fun handleException() {
        `when`(requester.url("https://www.marketwatch.com/investing/index/spx1")).thenReturn("")

        assertThrows<StockDAOException> { moscowExchangeDAO.getStockByTicker("spx1") }
    }
}

