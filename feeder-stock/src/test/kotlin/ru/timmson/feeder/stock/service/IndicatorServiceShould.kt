package ru.timmson.feeder.stock.service

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.any
import org.mockito.kotlin.eq
import org.mockito.kotlin.verify
import ru.timmson.feeder.stock.dao.*
import ru.timmson.feeder.stock.model.Indicator
import java.math.BigDecimal

@ExtendWith(MockitoExtension::class)
class IndicatorServiceShould {

    private lateinit var indicatorService: IndicatorService

    @Mock
    private lateinit var currencyRateDAO: CurrencyRateDAO

    @Mock
    private lateinit var moscowExchangeDAO: MoscowExchangeDAO

    @Mock
    private lateinit var stockStorageDAO: StockStorageDAO

    @Mock
    private lateinit var mainInfoDAO: MainInfoDAO

    @Mock
    private lateinit var stockFileStorageService: StockFileStorageService

    @BeforeEach
    fun setUp() {
        indicatorService = IndicatorService(
            currencyRateDAO = currencyRateDAO,
            moscowExchangeDAO = moscowExchangeDAO,
            stockStorageDAO = stockStorageDAO,
            mainInfoDAO = mainInfoDAO,
            stockFileStorageService = stockFileStorageService
        )
    }

    @Test
    fun `find all successfully`() {
        val indicator = Indicator("", BigDecimal(2))

        `when`(currencyRateDAO.getStockByTicker(any())).thenReturn(indicator)
        `when`(moscowExchangeDAO.getStockByTicker(any())).thenReturn(indicator)
        `when`(stockStorageDAO.getStockByTicker(any())).thenReturn(indicator)
        `when`(mainInfoDAO.getStockByTicker(any())).thenThrow(StockDAOException(Exception()))
        val actual = indicatorService.findAll()

        assertEquals(6, actual.size)
    }

    @Test
    fun `put successfully`() {
        val indicator = Indicator("", BigDecimal(2))

        indicatorService.put(indicator)

        verify(stockFileStorageService).setStock(eq(indicator))
    }
}
