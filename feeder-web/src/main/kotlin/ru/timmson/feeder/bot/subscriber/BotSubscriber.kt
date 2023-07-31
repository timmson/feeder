package ru.timmson.feeder.bot.subscriber

import com.pengrad.telegrambot.model.Update

interface BotSubscriber {

    fun receiveUpdate(update: Update)

}
