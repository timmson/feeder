package ru.timmson.feeder.stock.service

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.any
import ru.timmson.feeder.stock.dao.CentralBankDAO
import ru.timmson.feeder.stock.dao.MarketWatchDAO
import ru.timmson.feeder.stock.dao.MoscowExchangeDAO
import ru.timmson.feeder.stock.model.Indicator
import ru.timmson.feeder.stock.model.MainInfo
import java.math.BigDecimal

@ExtendWith(MockitoExtension::class)
class IndicatorServiceShould {

    private lateinit var indicatorService: IndicatorService

    @Mock
    private lateinit var moscowExchangeDAO: MoscowExchangeDAO

    @Mock
    private lateinit var marketWatchDAO: MarketWatchDAO

    @Mock
    private lateinit var centralBankDAO: CentralBankDAO


    @BeforeEach
    fun setUp() {
        indicatorService = IndicatorService(centralBankDAO, moscowExchangeDAO, marketWatchDAO)
    }

    @Test
    fun findAllSuccessfully() {
        val indicator = Indicator("", BigDecimal(2))

        `when`(centralBankDAO.getMainInfo()).thenReturn(MainInfo(BigDecimal.ZERO, BigDecimal.ZERO))
        `when`(moscowExchangeDAO.getStockByTicker(any())).thenReturn(indicator)
        `when`(marketWatchDAO.getStockByTicker(any())).thenReturn(indicator)
        val actual = indicatorService.findAll()

        assertEquals(7, actual.size)
    }
}
