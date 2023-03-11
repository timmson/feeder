package ru.timmson.feeder.bot

import com.pengrad.telegrambot.response.SendResponse
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.any
import org.mockito.kotlin.eq
import org.mockito.kotlin.verify
import ru.timmson.feeder.Version
import ru.timmson.feeder.common.FeederConfig

@ExtendWith(MockitoExtension::class)
class BotServiceShould {

    private lateinit var botService: BotService

    @Mock
    private lateinit var botProxy: BotProxy

    private val feederConfig = FeederConfig()

    @Mock
    private lateinit var botListener: BotListener

    @Mock
    private lateinit var version: Version

    @Mock
    private lateinit var response: SendResponse

    @BeforeEach
    fun setUp() {
        botService = BotService(botProxy, feederConfig, botListener, version)
    }

    @Test
    fun startupBotAndSetListenersAndSendMessageToOwner() {
        feederConfig.token = "X"
        `when`(response.isOk).thenReturn(true)
        `when`(botProxy.execute(any())).thenReturn(response)

        botService.postConstruct()

        verify(botProxy).startup(eq(feederConfig.token))
        verify(botProxy).setUpdatesListener(eq(botListener))
        verify(botProxy).execute(any())
    }

    @Test
    fun shutdownBotAndRemoveListeners() {
        botService.preDestroy()

        verify(botProxy).removeGetUpdatesListener()
        verify(botProxy).shutdown()
    }
}
