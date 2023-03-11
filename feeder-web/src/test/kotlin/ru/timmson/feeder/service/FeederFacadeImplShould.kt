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
import ru.timmson.feeder.bot.BotServiceImpl
import ru.timmson.feeder.common.FeederConfig
import ru.timmson.feeder.stock.model.Stock
import ru.timmson.feeder.stock.service.StockServiceImpl
import java.math.BigDecimal

@ExtendWith(MockitoExtension::class)
class FeederFacadeImplShould {

    private lateinit var feederFacade: FeederFacade

    private lateinit var feederConfig: FeederConfig

    @Mock
    private lateinit var stockService: StockServiceImpl

    @Mock
    private lateinit var botService: BotServiceImpl

    @BeforeEach
    fun setUp() {
        feederConfig = FeederConfig()
        feederFacade = FeederFacade(feederConfig, stockService, botService)
    }

    @Test
    fun sendStocksToOwner() {
        val stocks = listOf(
            Stock("usd", BigDecimal(10)),
            Stock("spx", BigDecimal(20))
        )
        `when`(stockService.findAll()).thenReturn(stocks)

        feederFacade.sendStocksToOwner()

        verify(botService).sendMessageToOwner(eq("💰10, 🇺🇸20"))
    }

    @Test
    fun sendStocksToChannel() {
        feederConfig.stockChannelId = "channelId"
        val stocks = listOf(
            Stock("usd", BigDecimal(10)),
            Stock("spx", BigDecimal(20))
        )
        `when`(stockService.findAll()).thenReturn(stocks)

        feederFacade.sendStocksToChannel()

        verify(botService).sendMessage(eq(feederConfig.stockChannelId), eq("💰10, 🇺🇸20"))
    }
}
