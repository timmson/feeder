package ru.timmson.feeder.bot.subscriber

import com.pengrad.telegrambot.model.Chat
import com.pengrad.telegrambot.model.Document
import com.pengrad.telegrambot.model.Message
import com.pengrad.telegrambot.model.Update
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
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

    @Spy
    private lateinit var document: Document

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
        botDispatcher.receiveUpdate(update)

        verify(botService).sendMessage(eq(senderId), eq(expected))
        verifyNoInteractions(feederFacade)
    }

    @Test
    fun sendSorryWhenMessageIsNotOfAcceptableFormat() {
        val expected = "This message does not contain any of known formats ;("
        val chatId = 1L
        feederConfig.ownerId = chatId.toString()

        doReturn(chatId).`when`(chat).id()
        botDispatcher.receiveUpdate(update)

        verify(botService).sendMessage(eq(chatId), eq(expected))
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

    @Test
    fun askFacadeForMeaningOfTheWord() {
        val chatId = 1L
        feederConfig.ownerId = chatId.toString()
        val expected = "some word"

        doReturn("/w $expected").`when`(message).text()
        doReturn(chatId).`when`(chat).id()
        botDispatcher.receiveUpdate(update)

        verifyNoInteractions(botService)
        verify(feederFacade).sendMeaningAndTranslation(eq(chatId.toString()), eq(expected))
    }

    @Test
    fun receiveCV() {
        val chatId = 1L
        val messageId = 1
        val forwardDate = 1000
        val caption = "some text"
        val fileName = "file name"

        feederConfig.ownerId = chatId.toString()
        doReturn(document).`when`(message).document()
        `when`(message.forwardFromMessageId()).thenReturn(messageId)
        `when`(message.forwardDate()).thenReturn(forwardDate)
        `when`(message.caption()).thenReturn(caption)
        `when`(document.fileName()).thenReturn(fileName)
        doReturn(chatId).`when`(chat).id()

        botDispatcher.receiveUpdate(update)

        verify(feederFacade).registerCV(any())
        verifyNoInteractions(botService)
    }

    @Test
    fun receiveIncorrectCV() {
        val expected = "This document has an incorrect fields: caption(...) must not be null"
        val chatId = 1L

        feederConfig.ownerId = chatId.toString()
        doReturn(document).`when`(message).document()
        doReturn(chatId).`when`(chat).id()

        botDispatcher.receiveUpdate(update)

        verifyNoInteractions(feederFacade)
        verify(botService).sendMessage(eq(chatId), eq(expected))
    }
}
