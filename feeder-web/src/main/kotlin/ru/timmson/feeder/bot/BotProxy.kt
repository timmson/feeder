package ru.timmson.feeder.bot

import com.pengrad.telegrambot.TelegramBot
import com.pengrad.telegrambot.UpdatesListener
import com.pengrad.telegrambot.request.SendMessage
import com.pengrad.telegrambot.response.SendResponse
import org.springframework.stereotype.Service

@Service
class BotProxy {

    private lateinit var bot: TelegramBot

    fun startup(token: String) {
        bot = TelegramBot(token)
    }

    fun setUpdatesListener(updatesListener: UpdatesListener) = bot.setUpdatesListener(updatesListener)

    fun execute(sendMessage: SendMessage): SendResponse = bot.execute(sendMessage)

    fun removeGetUpdatesListener() = bot.removeGetUpdatesListener()

    fun shutdown() = bot.shutdown()

}
