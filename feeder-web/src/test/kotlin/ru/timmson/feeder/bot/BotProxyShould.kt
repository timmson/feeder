package ru.timmson.feeder.bot

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.junit.jupiter.MockitoExtension
import ru.timmson.feeder.bot.model.request.SendMessage

@ExtendWith(MockitoExtension::class)
class BotProxyShould {

    private lateinit var botProxy: BotProxy

    @BeforeEach
    fun setUp() {
        botProxy = BotProxy()
    }

    @Test
    fun redirectCallsToBot() {
        botProxy.startup("")
        botProxy.setUpdatesListener { _ -> 0 }
        botProxy.execute(SendMessage(0, ""))
        botProxy.removeGetUpdatesListener()
        botProxy.shutdown()
    }

}
