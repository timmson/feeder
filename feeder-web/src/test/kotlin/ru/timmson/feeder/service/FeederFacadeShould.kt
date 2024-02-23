package ru.timmson.feeder.service

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.any
import org.mockito.kotlin.eq
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import ru.timmson.feeder.bot.BotService
import ru.timmson.feeder.common.FeederConfig
import ru.timmson.feeder.cv.CV
import ru.timmson.feeder.cv.CVRegistrar
import ru.timmson.feeder.cv.CVStore
import ru.timmson.feeder.cv.model.CVRegisterRequest
import ru.timmson.feeder.stock.model.Stock
import ru.timmson.feeder.stock.service.StockService
import java.math.BigDecimal

@ExtendWith(MockitoExtension::class)
class FeederFacadeShould {

    private lateinit var feederFacade: FeederFacade

    private lateinit var feederConfig: FeederConfig

    @Mock
    private lateinit var stockService: StockService

    @Mock
    private lateinit var cvRegistrar: CVRegistrar

    @Mock
    private lateinit var cvStore: CVStore

    @Mock
    private lateinit var botService: BotService

    @BeforeEach
    fun setUp() {
        feederConfig = FeederConfig()
        feederFacade = FeederFacade(feederConfig, stockService, cvRegistrar, cvStore, botService)
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

    @Test
    fun registerCV() {
        val chatId = 1L
        val forwardedChatId = 2L
        val messageId = 3
        val messageTimestamp = "date"
        val caption = "caption"
        val fileName = "fileName"
        val request = CVRegisterRequest(forwardedChatId = forwardedChatId, forwardedMessageId = messageId, caption = caption, fileName = fileName)
        val cv = CV()
        val cvRequest = RegisterCVRequest(chatId, forwardedChatId, messageId, messageTimestamp, caption, fileName)

        `when`(cvRegistrar.parse(eq(request))).thenReturn(cv)
        feederFacade.registerCV(cvRequest)

        verify(botService, times(1)).sendMessage(any())
        verify(cvStore, times(1)).add(any())
    }


}
