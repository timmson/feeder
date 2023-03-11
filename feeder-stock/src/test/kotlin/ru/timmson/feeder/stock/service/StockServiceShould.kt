package ru.timmson.feeder.stock.service

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.any
import ru.timmson.feeder.stock.dao.MarketWatchDAO
import ru.timmson.feeder.stock.dao.MoscowExchangeDAO
import ru.timmson.feeder.stock.model.Stock
import java.math.BigDecimal

@ExtendWith(MockitoExtension::class)
class StockServiceShould {

    private lateinit var stockService: StockService

    @Mock
    private lateinit var moscowExchangeDAO: MoscowExchangeDAO

    @Mock
    private lateinit var marketWatchDAO: MarketWatchDAO

    @BeforeEach
    fun setUp() {
        stockService = StockService(moscowExchangeDAO, marketWatchDAO)
    }

    @Test
    fun findAllSuccessfully() {
        val stock = Stock("", BigDecimal(2))
        val expected = stock.price.multiply(BigDecimal(stockService.getTickers().size))

        `when`(moscowExchangeDAO.getStockByTicker(any())).thenReturn(stock)
        `when`(marketWatchDAO.getStockByTicker(any())).thenReturn(stock)
        val actual = stockService.findAll().sumOf { it.price }

        assertEquals(expected, actual)
    }

    @Test
    fun findAllExceptOneWhenCacheIsEmpty() {
        val stock = Stock("", BigDecimal(3))
        val expected = stock.price.multiply(BigDecimal(stockService.getTickers().size - 1))

        `when`(moscowExchangeDAO.getStockByTicker(any()))
            .thenReturn(stock, stock, stock, stock, stock)
            .thenThrow(NumberFormatException())
        `when`(marketWatchDAO.getStockByTicker(any()))
            .thenReturn(stock)

        stockService.findAll().sumOf { it.price }
        stockService.resetCache()
        val actual = stockService.findAll().sumOf { it.price }

        assertEquals(expected, actual)
    }

    @Test
    fun findAllWhenCashIsFullFilled() {
        val stock = Stock("", BigDecimal(4))
        val expected = stock.price.multiply(BigDecimal(stockService.getTickers().size))

        `when`(moscowExchangeDAO.getStockByTicker(any()))
            .thenReturn(stock, stock, stock, stock, stock)
            .thenThrow(NumberFormatException())
        `when`(marketWatchDAO.getStockByTicker(any()))
            .thenReturn(stock)

        stockService.findAll().sumOf { it.price }

        val actual = stockService.findAll().sumOf { it.price }

        assertEquals(expected, actual)
    }
}
