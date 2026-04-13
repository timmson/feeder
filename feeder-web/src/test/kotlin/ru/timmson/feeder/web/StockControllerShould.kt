package ru.timmson.feeder.web

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.whenever
import ru.timmson.feeder.stock.model.Indicator
import ru.timmson.feeder.stock.service.IndicatorService
import java.math.BigDecimal

@ExtendWith(MockitoExtension::class)
class StockControllerShould {

    @Mock
    private lateinit var indicatorService: IndicatorService

    private lateinit var stockController: StockController

    @Test
    fun `return all indicators`() {
        val indicators = listOf(
            Indicator("usd", BigDecimal("96.50")),
            Indicator("eur", BigDecimal("105.20")),
            Indicator("imoex", BigDecimal("3200.45"))
        )

        stockController = StockController(indicatorService)
        whenever(indicatorService.findAll()).thenReturn(indicators)

        val result = stockController.getStocks()

        assertEquals(3, result.size)
        assertEquals("usd", result[0].name)
        assertEquals(BigDecimal("96.50"), result[0].price)
        assertEquals("eur", result[1].name)
        assertEquals(BigDecimal("105.20"), result[1].price)
        assertEquals("imoex", result[2].name)
        assertEquals(BigDecimal("3200.45"), result[2].price)
    }

    @Test
    fun `return empty list when no indicators`() {
        stockController = StockController(indicatorService)
        whenever(indicatorService.findAll()).thenReturn(emptyList())

        val result = stockController.getStocks()

        assertEquals(0, result.size)
    }
}
