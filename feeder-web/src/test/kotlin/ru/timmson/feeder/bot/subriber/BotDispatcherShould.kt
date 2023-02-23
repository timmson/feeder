package ru.timmson.feeder.bot.subriber

import com.pengrad.telegrambot.model.Chat
import com.pengrad.telegrambot.model.Message
import com.pengrad.telegrambot.model.Update
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.Mock
import org.mockito.Spy
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.*
import ru.timmson.feeder.bot.BotListener
import ru.timmson.feeder.bot.BotService
import ru.timmson.feeder.common.FeederConfig
import ru.timmson.feeder.service.FeederFacade

@ExtendWith(MockitoExtension::class)
class BotDispatcherShould {

    private lateinit var botDispatcher: BotDispatcher

    private val feederConfig = FeederConfig()

    @Mock
    private lateinit var botService: BotService

    @Mock
    private lateinit var feederFacade: FeederFacade

    @Mock
    private lateinit var botListener: BotListener

    @Spy
    private lateinit var update: Update

    @Spy
    private lateinit var message: Message

    @Spy
    private lateinit var chat: Chat

    @Captor
    private lateinit var textCaptor: ArgumentCaptor<String>

    @BeforeEach
    fun setUp() {
        botDispatcher = BotDispatcher(feederConfig, botService, feederFacade, botListener)

        doReturn(message).`when`(update).message()
        doReturn(chat).`when`(message).chat()
    }

    @Test
    fun sendSorryWhenMessageIsNotFromOwner() {
        val expected = "Sorry :("
        feederConfig.ownerId = 1L.toString()
        val senderId = 2L

        doReturn(senderId).`when`(chat).id()
        doNothing().`when`(botService).sendMessage(eq(senderId), capture(textCaptor))
        botDispatcher.receiveUpdate(update)
        val actual = textCaptor.value

        assertEquals(expected, actual)
        verifyNoInteractions(feederFacade)
    }

    @Test
    fun askFacadeForSendingStocks() {
        val chatId = 1L
        feederConfig.ownerId = chatId.toString()

        doReturn("/stock").`when`(message).text()
        doReturn(chatId).`when`(chat).id()
        botDispatcher.receiveUpdate(update)

        verifyNoInteractions(botService)
        verify(feederFacade).sendStocksToOwner()
    }
}