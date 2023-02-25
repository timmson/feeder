package ru.timmson.feeder.bot

import com.pengrad.telegrambot.UpdatesListener
import com.pengrad.telegrambot.model.Update
import ru.timmson.feeder.bot.subriber.BotSubscriber

interface BotListener : UpdatesListener {

    override fun process(updates: MutableList<Update>?): Int

    fun subscribe(subscriber: BotSubscriber)

    fun unsubscribe(subscriber: BotSubscriber)
}