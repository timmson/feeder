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
import ru.timmson.feeder.cv.CVRegisterRequest
import ru.timmson.feeder.cv.CVRegistrar
import ru.timmson.feeder.lingua.LinguaService
import ru.timmson.feeder.lingua.oxford.model.Meaning
import ru.timmson.feeder.lingua.oxford.model.OxfordDictionaryExplainResponse
import ru.timmson.feeder.lingua.translate.model.LinguaLeoTranslation
import ru.timmson.feeder.lingua.translate.model.LinguaLeoTranslationResponse
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
    private lateinit var linguaService: LinguaService

    @Mock
    private lateinit var cvRegistrar: CVRegistrar

    @Mock
    private lateinit var printService: PrintService

    @Mock
    private lateinit var botService: BotService

    @BeforeEach
    fun setUp() {
        feederConfig = FeederConfig()
        feederFacade = FeederFacade(feederConfig, stockService, linguaService, cvRegistrar, printService, botService)
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
    fun sendMeaning() {
        val chatId = "1"
        val word = "some word"
        val translation = "some translation"
        val explanation = "some meaning"
        val linguaLeoTranslationResponse = LinguaLeoTranslationResponse().apply {
            url = ""
            translate = listOf(LinguaLeoTranslation().apply {
                value = translation
            })
        }
        val oxfordDictionaryExplainResponse = OxfordDictionaryExplainResponse(url = "", meanings = listOf(Meaning(explanation)))

        `when`(linguaService.explain(eq(word))).thenReturn(oxfordDictionaryExplainResponse)
        `when`(linguaService.translate(eq(word))).thenReturn(linguaLeoTranslationResponse)

        feederFacade.sendMeaningAndTranslation(chatId, word)

        verify(botService, times(2)).sendMessage(eq(chatId), any())
    }

    @Test
    fun registerCV() {
        val chatId = "1"
        val messageId = 2
        val messageTimestamp = 1231313
        val caption = "caption"
        val fileName = "fileName"
        val request = CVRegisterRequest(caption = caption, fileName = fileName)
        val cv = CV()
        val cvRequest = RegisterCVRequest(chatId, messageId, messageTimestamp, caption, fileName)

        `when`(cvRegistrar.parse(eq(request))).thenReturn(cv)
        `when`(printService.printCV(eq(cv), any())).thenReturn("")
        feederFacade.registerCV(cvRequest)

        verify(botService, times(1)).sendMessage(any())
    }


}
