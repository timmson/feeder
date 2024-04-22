package ru.timmson.feeder.stock.dao

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.eq
import ru.timmson.feeder.stock.model.Indicator
import ru.timmson.feeder.stock.service.StockFileStorageService
import java.math.BigDecimal
import java.math.RoundingMode

@ExtendWith(MockitoExtension::class)
class StockStorageDAOShould {

    private lateinit var stockStorageDAO: StockStorageDAO

    @Mock
    private lateinit var stockFileStorageService: StockFileStorageService

    @BeforeEach
    fun setUp() {
        stockStorageDAO = StockStorageDAO(stockFileStorageService)
    }

    @Test
    fun `read stock price from file successfully`() {
        val expected = Indicator("spx", BigDecimal(4079.90).setScale(2, RoundingMode.HALF_UP))

        `when`(stockFileStorageService.getStockByTicker(eq(expected.name))).thenReturn(expected)
        val actual = stockStorageDAO.getStockByTicker(expected.name)

        assertEquals(expected, actual)
    }
}

