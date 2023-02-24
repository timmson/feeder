package ru.timmson.feeder.service

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.eq
import org.mockito.kotlin.verify
import ru.timmson.feeder.bot.BotService
import ru.timmson.feeder.stock.model.Stock
import ru.timmson.feeder.stock.service.StockService
import java.math.BigDecimal

@ExtendWith(MockitoExtension::class)
class FeederFacadeImplShould {

    private lateinit var feederFacade: FeederFacadeImpl

    @Mock
    private lateinit var stockService: StockService

    @Mock
    private lateinit var botService: BotService

    @BeforeEach
    fun setUp() {
        feederFacade = FeederFacadeImpl(stockService, botService)
    }

    @Test
    fun sendStocksToOwner() {
        val stocks = listOf<Stock>(
            Stock("usd", BigDecimal(10)),
            Stock("spx", BigDecimal(20))
        )
        `when`(stockService.findAll()).thenReturn(stocks)

        feederFacade.sendStocksToOwner()

        verify(botService).sendMessageToOwner(eq("💰10, 🇺🇸20"))
    }
}