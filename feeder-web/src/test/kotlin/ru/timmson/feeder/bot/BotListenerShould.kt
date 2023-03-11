package ru.timmson.feeder.bot

import com.pengrad.telegrambot.model.Update
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.*
import ru.timmson.feeder.bot.subriber.BotSubscriber

@ExtendWith(MockitoExtension::class)
class BotListenerShould {

    private lateinit var botListener: BotListener

    @Mock
    private lateinit var botSubscriber: BotSubscriber

    @BeforeEach
    fun setUp() {
        botListener = BotListener()
    }

    @Test
    fun callSubscribers() {
        botListener.subscribe(botSubscriber)

        botListener.process(mutableListOf(Update(), Update()))

        verify(botSubscriber, times(2)).receiveUpdate(any())
    }

    @Test
    fun notCallSubscribers() {
        botListener.subscribe(botSubscriber)
        botListener.unsubscribe(botSubscriber)

        botListener.process(mutableListOf(Update(), Update()))

        verifyNoInteractions(botSubscriber)
    }

}
