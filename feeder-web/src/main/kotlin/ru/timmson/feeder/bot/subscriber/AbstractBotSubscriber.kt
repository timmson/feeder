package ru.timmson.feeder.bot.subscriber

import jakarta.annotation.PostConstruct
import jakarta.annotation.PreDestroy
import ru.timmson.feeder.bot.BotListener

abstract class AbstractBotSubscriber(
    private val botListener: BotListener
) : BotSubscriber {

    @PostConstruct
    fun subscribe() = botListener.subscribe(this)

    @PreDestroy
    fun unsubscribe() = botListener.unsubscribe(this)

}
