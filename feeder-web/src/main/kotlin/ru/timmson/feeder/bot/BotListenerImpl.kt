package ru.timmson.feeder.bot

import com.pengrad.telegrambot.UpdatesListener
import com.pengrad.telegrambot.model.Update
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.springframework.stereotype.Service
import ru.timmson.feeder.bot.subriber.BotSubscriber
import ru.timmson.feeder.common.logger

@Service
class BotListenerImpl : BotListener {

    private val log = logger<BotListenerImpl>()

    private val subscribers: MutableSet<BotSubscriber> = HashSet()

    override fun process(updates: MutableList<Update>?): Int = runBlocking {
        handle(updates ?: emptyList())
        UpdatesListener.CONFIRMED_UPDATES_ALL
    }

    override fun handle(updates: List<Update>) = runBlocking {
        updates.forEach { u ->
            launch {
                subscribers.forEach { s ->
                    launch {
                        s.receiveUpdate(u)
                    }
                }
            }
        }
    }

    override fun subscribe(subscriber: BotSubscriber) {
        log.info("${subscriber.javaClass.simpleName} subscribed to BotUpdates")
        subscribers.add(subscriber)
    }

    override fun unsubscribe(subscriber: BotSubscriber) {
        log.info("${subscriber.javaClass.simpleName} unsubscribed from BotUpdates")
        subscribers.remove(subscriber)
    }

}