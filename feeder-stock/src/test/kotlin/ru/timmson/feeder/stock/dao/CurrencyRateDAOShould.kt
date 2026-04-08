package ru.timmson.feeder.stock.dao

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.any
import org.mockito.kotlin.eq
import org.mockito.kotlin.never
import ru.timmson.feeder.stock.model.Indicator
import ru.timmson.feeder.stock.service.StockFileStorageService
import java.math.BigDecimal
import java.time.LocalDate

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
    fun `return indicator when ticker exists`() {
        val expected = Indicator("USD", BigDecimal("92.50"))
        val tomorrow = LocalDate.now().plusDays(1)

        `when`(centralBankAPI.getCursInfo(eq(tomorrow))).thenReturn(mapOf("USD" to expected))

        val actual = currencyRateDAO.getStockByTicker("USD")

        assertEquals(expected, actual)
        verify(stockFileStorageService).setStock(expected)
    }

    @Test
    fun `call API with tomorrow's date`() {
        val expected = Indicator("EUR", BigDecimal.ONE)
        val tomorrow = LocalDate.now().plusDays(1)

        `when`(centralBankAPI.getCursInfo(eq(tomorrow))).thenReturn(mapOf("EUR" to expected))

        currencyRateDAO.getStockByTicker("EUR")

        verify(centralBankAPI).getCursInfo(eq(tomorrow))
    }

    @Test
    fun `throw NullPointerException when ticker not found`() {
        val tomorrow = LocalDate.now().plusDays(1)

        `when`(centralBankAPI.getCursInfo(eq(tomorrow))).thenReturn(emptyMap())

        assertThrows(NullPointerException::class.java) {
            currencyRateDAO.getStockByTicker("UNKNOWN")
        }

        verify(stockFileStorageService, never()).setStock(any())
    }
}
