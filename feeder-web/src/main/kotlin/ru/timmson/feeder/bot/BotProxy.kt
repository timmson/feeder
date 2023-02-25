package ru.timmson.feeder.bot

import com.pengrad.telegrambot.UpdatesListener
import com.pengrad.telegrambot.request.SendMessage
import com.pengrad.telegrambot.response.SendResponse

interface BotProxy {

    fun startup(token: String)

    fun setUpdatesListener(updatesListener: UpdatesListener)

    fun execute(sendMessage: SendMessage): SendResponse

    fun removeGetUpdatesListener()

    fun shutdown()

}