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
class StockServiceImplShould {

    private lateinit var stockService: StockServiceImpl

    @Mock
    private lateinit var moscowExchangeDAO: MoscowExchangeDAO

    @Mock
    private lateinit var marketWatchDAO: MarketWatchDAO

    @BeforeEach
    fun setUp() {
        stockService = StockServiceImpl(moscowExchangeDAO, marketWatchDAO)
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
    fun findAllExceptOne() {
        val stock = Stock("", BigDecimal(2))
        val expected = stock.price.multiply(BigDecimal(stockService.getTickers().size - 1))

        `when`(moscowExchangeDAO.getStockByTicker(any()))
            .thenReturn(stock).thenReturn(stock).thenThrow(NumberFormatException())
        `when`(marketWatchDAO.getStockByTicker(any())).thenReturn(stock)

        val actual = stockService.findAll().sumOf { it.price }

        assertEquals(expected, actual)
    }
}