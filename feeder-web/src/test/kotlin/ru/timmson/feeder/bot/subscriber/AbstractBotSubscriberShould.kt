package ru.timmson.feeder.bot.subscriber

import com.pengrad.telegrambot.model.Update
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.any
import org.mockito.kotlin.verify
import ru.timmson.feeder.bot.BotListener

@ExtendWith(MockitoExtension::class)
class AbstractBotSubscriberShould {


    private lateinit var abstractBotSubscriber: AbstractBotSubscriber

    @Mock
    private lateinit var botListener: BotListener

    @BeforeEach
    fun setUp() {
        abstractBotSubscriber = object : AbstractBotSubscriber(botListener) {
            override fun receiveUpdate(update: Update) {

            }
        }
    }

    @Test
    fun subscribe() {
        abstractBotSubscriber.subscribe()
        verify(botListener).subscribe(any())
    }

    @Test
    fun unsubscribe() {
        abstractBotSubscriber.unsubscribe()
        verify(botListener).unsubscribe(any())
    }
}
