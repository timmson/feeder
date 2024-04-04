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
import ru.timmson.feeder.stock.model.Indicator
import ru.timmson.feeder.stock.service.IndicatorService
import java.math.BigDecimal

@ExtendWith(MockitoExtension::class)
class FeederFacadeShould {

    private lateinit var feederFacade: FeederFacade

    private lateinit var feederConfig: FeederConfig

    @Mock
    private lateinit var indicatorService: IndicatorService

    @Mock
    private lateinit var cvRegistrar: CVRegistrar

    @Mock
    private lateinit var cvStore: CVStore

    @Mock
    private lateinit var botService: BotService

    @BeforeEach
    fun setUp() {
        feederConfig = FeederConfig()
        feederFacade = FeederFacade(feederConfig, indicatorService, cvRegistrar, cvStore, botService)
    }

    @Test
    fun sendStocksToOwner() {
        val indicators = listOf(
            Indicator("usd", BigDecimal(10)),
            Indicator("spx", BigDecimal(20))
        )
        `when`(indicatorService.findAll()).thenReturn(indicators)

        feederFacade.sendStocksToOwner()

        verify(botService).sendMessageToOwner(eq("💰 Курс USD: <b>10,00 руб.</b>\n🇺🇸 S&P 500 Index: <b>20</b>"))
    }

    @Test
    fun sendStocksToChannel() {
        feederConfig.stockChannelId = "channelId"
        val indicators = listOf(
            Indicator("usd", BigDecimal(10)),
            Indicator("spx", BigDecimal(20))
        )
        `when`(indicatorService.findAll()).thenReturn(indicators)

        feederFacade.sendStocksToChannel()

        verify(botService).sendMessage(eq(feederConfig.stockChannelId), eq("💰 Курс USD: <b>10,00 руб.</b>\n🇺🇸 S&P 500 Index: <b>20</b>"))
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

        verify(botService, times(1)).sendMessage(eq(1L), eq("<code>Fields(name=, area=, title=, type=, date=date, url=)</code>"))
        verify(cvStore, times(1)).add(any())
    }


}
