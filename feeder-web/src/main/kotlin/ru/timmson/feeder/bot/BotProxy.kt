package ru.timmson.feeder.bot

import com.pengrad.telegrambot.TelegramBot
import com.pengrad.telegrambot.UpdatesListener
import com.pengrad.telegrambot.model.request.ParseMode
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

    fun execute(sendMessage: ru.timmson.feeder.bot.model.request.SendMessage): SendResponse =
        SendMessage(sendMessage.chatId, sendMessage.text).let {
            if (sendMessage.isHTML) {
                it.parseMode(ParseMode.HTML)
                it.disableWebPagePreview(true)
            }
            bot.execute(it)
        }

    fun removeGetUpdatesListener() = bot.removeGetUpdatesListener()

    fun shutdown() = bot.shutdown()

}
