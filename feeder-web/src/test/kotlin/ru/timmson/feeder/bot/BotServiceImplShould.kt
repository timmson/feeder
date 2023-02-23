package ru.timmson.feeder.bot

import com.pengrad.telegrambot.response.SendResponse
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Spy
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.any
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.eq
import org.mockito.kotlin.verify
import ru.timmson.feeder.common.FeederConfig

@ExtendWith(MockitoExtension::class)
class BotServiceImplShould {

    private lateinit var botService: BotServiceImpl

    @Mock
    private lateinit var botProxy: BotProxy

    private val feederConfig = FeederConfig()

    @Mock
    private lateinit var botListener: BotListener

    @Spy
    private lateinit var response: SendResponse

    @BeforeEach
    fun setUp() {
        botService = BotServiceImpl(botProxy, feederConfig, botListener)
    }

    @Test
    fun startupBotAndSetListenersAndSendMessageToOwner() {
        feederConfig.token = "X"
        doReturn(true).`when`(response).isOk
        `when`(botProxy.execute(any())).thenReturn(response)

        botService.postConstruct()

        verify(botProxy).startup(eq(feederConfig.token))
        verify(botProxy).setUpdatesListener(eq(botListener))
    }

    @Test
    fun shutdownBotAndRemoveListeners() {
        botService.preDestroy()

        verify(botProxy).removeGetUpdatesListener()
        verify(botProxy).shutdown()
    }
}