package ru.timmson.feeder.bot

import com.pengrad.telegrambot.request.SendMessage
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.junit.jupiter.MockitoExtension

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
