package ru.timmson.feeder.stock.dao

import junit.framework.TestCase.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.any
import ru.timmson.feeder.stock.model.Indicator
import ru.timmson.feeder.stock.service.StockFileStorageService
import java.math.BigDecimal

@ExtendWith(MockitoExtension::class)
class CurrencyRateDAOShould {

    private lateinit var currencyRateDAO: CurrencyRateDAO

    @Mock
    private lateinit var centralBankAPI: CentralBankAPI

    @Mock
    private lateinit var stockFileStorageService: StockFileStorageService

    @BeforeEach
    fun setUp() {
        currencyRateDAO = CurrencyRateDAO(
            centralBankAPI = centralBankAPI,
            stockFileStorageService = stockFileStorageService
        )
    }

    @Test
    fun `get stock by ticker`() {
        val expected = Indicator("xxx", BigDecimal.ONE)

        `when`(centralBankAPI.getCursInfo(any())).thenReturn(mapOf("xxx" to expected))
        val actual = currencyRateDAO.getStockByTicker("xxx")

        assertEquals(expected, actual)
    }
}
