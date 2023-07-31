package ru.timmson.feeder.bot

import com.pengrad.telegrambot.model.Message
import com.pengrad.telegrambot.model.Update
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.any
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoInteractions
import ru.timmson.feeder.bot.subscriber.BotSubscriber
import ru.timmson.feeder.common.injectValue

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

        botListener.process(mutableListOf(getUpdateWithMessage(), getUpdateWithMessage()))

        verify(botSubscriber, times(2)).receiveUpdate(any())
    }

    @Test
    fun notCallSubscribers() {
        botListener.subscribe(botSubscriber)
        botListener.unsubscribe(botSubscriber)

        botListener.process(mutableListOf(Update(), Update()))

        verifyNoInteractions(botSubscriber)
    }

    private fun getUpdateWithMessage(): Update = injectValue(Update(), "message", Message())

}
