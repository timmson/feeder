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
class MarketWatchDAOShould {

    private lateinit var marketWatchDAO: MarketWatchDAO

    @Mock
    private lateinit var requester: Requester


    @BeforeEach
    fun setUp() {
        marketWatchDAO = MarketWatchDAO(requester)
    }


    @Test
    fun returnPriceOfSNP500() {
        val expected = Stock("spx", BigDecimal(4079.09).setScale(2, RoundingMode.HALF_UP))

        `when`(requester.fetch("https://www.marketwatch.com/investing/index/spx")).thenReturn("<meta name=\"price\" content=\"4,079.09\" />")
        val actual = marketWatchDAO.getStockByTicker("spx")

        assertEquals(expected, actual)
    }

    @Test
    fun returnPriceOfSChinaComposite() {
        val expected = Stock("shcomp", BigDecimal(3079.09).setScale(2, RoundingMode.HALF_UP))

        `when`(requester.fetch("https://www.marketwatch.com/investing/index/shcomp?countrycode=cn")).thenReturn("<meta name=\"price\" content=\"3,079.09\" />")
        val actual = marketWatchDAO.getStockByTicker("shcomp")

        assertEquals(expected, actual)
    }

    @Test
    fun handleException() {
        `when`(requester.fetch("https://www.marketwatch.com/investing/index/spx1")).thenReturn("")

        assertThrows<StockDAOException> { marketWatchDAO.getStockByTicker("spx1") }
    }
}

