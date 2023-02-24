package ru.timmson.feeder.bot

import com.pengrad.telegrambot.TelegramBot
import com.pengrad.telegrambot.UpdatesListener
import com.pengrad.telegrambot.request.SendMessage
import com.pengrad.telegrambot.response.SendResponse
import org.springframework.stereotype.Service

@Service
class BotProxyImpl : BotProxy {

    private lateinit var bot: TelegramBot

    override fun startup(token: String) {
        bot = TelegramBot(token)
    }

    override fun setUpdatesListener(updatesListener: UpdatesListener) = bot.setUpdatesListener(updatesListener)

    override fun execute(sendMessage: SendMessage): SendResponse = bot.execute(sendMessage)

    override fun removeGetUpdatesListener() = bot.removeGetUpdatesListener()

    override fun shutdown() = bot.shutdown()

}