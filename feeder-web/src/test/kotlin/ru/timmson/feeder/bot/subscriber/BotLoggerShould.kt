package ru.timmson.feeder.bot.subscriber

import com.pengrad.telegrambot.model.Chat
import com.pengrad.telegrambot.model.Message
import com.pengrad.telegrambot.model.Update
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.Spy
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.verify
import ru.timmson.feeder.bot.BotListener

@ExtendWith(MockitoExtension::class)
class BotLoggerShould {

    private lateinit var botLogger: BotLogger

    @Mock
    private lateinit var botListener: BotListener

    @Spy
    private lateinit var update: Update

    @Spy
    private lateinit var message: Message

    @Spy
    private lateinit var chat: Chat

    @BeforeEach
    fun setUp() {
        botLogger = BotLogger(botListener)

        doReturn(message).`when`(update).message()
        doReturn(chat).`when`(message).chat()
    }


    @Test
    fun receiveUpdate() {
        botLogger.receiveUpdate(update)

        verify(message).text()
        verify(chat).id()
    }
}
