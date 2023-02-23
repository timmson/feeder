package ru.timmson.feeder.bot.subriber

import com.pengrad.telegrambot.model.Update

interface BotSubscriber {

    fun receiveUpdate(update: Update)

}