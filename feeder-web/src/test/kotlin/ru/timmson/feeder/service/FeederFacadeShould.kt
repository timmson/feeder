package ru.timmson.feeder.service

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.eq
import org.mockito.kotlin.verify
import ru.timmson.feeder.bot.BotService
import ru.timmson.feeder.common.FeederConfig
import ru.timmson.feeder.cv.CVRegistrar
import ru.timmson.feeder.cv.CVStore
import ru.timmson.feeder.stock.model.Indicator
import ru.timmson.feeder.stock.service.IndicatorService
import java.math.BigDecimal

@ExtendWith(MockitoExtension::class)
class FeederFacadeShould {

    private lateinit var feederFacade: FeederFacade

    @Mock
    private lateinit var feederConfig: FeederConfig

    @Mock
    private lateinit var indicatorService: IndicatorService

    @Mock
    private lateinit var cvRegistrar: CVRegistrar

    @Mock
    private lateinit var cvStore: CVStore

    @Mock
    private lateinit var botService: BotService

    @Mock
    private lateinit var cvEstimationService: CVEstimationService

    @BeforeEach
    fun setUp() {
        feederFacade = FeederFacade(
            feederConfig = feederConfig,
            indicatorService = indicatorService,
            cvRegistrar = cvRegistrar,
            cvStore = cvStore,
            botService = botService,
            cvEstimationService = cvEstimationService
        )
    }

    @Test
    fun `put successfully`() {
        val indicator = Indicator("spx", BigDecimal("4000.9"))

        feederFacade.putStock("/stock ${indicator.name} ${indicator.price}")

        verify(indicatorService).put(eq(indicator))
        verify(botService).sendMessageToOwner(eq("ok"))
    }
}
