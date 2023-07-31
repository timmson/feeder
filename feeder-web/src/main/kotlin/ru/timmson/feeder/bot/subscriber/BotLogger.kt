package ru.timmson.feeder.bot.subscriber

import com.pengrad.telegrambot.model.Update
import org.springframework.stereotype.Service
import ru.timmson.feeder.bot.BotListener
import ru.timmson.feeder.common.logger

@Service
class BotLogger(botListener: BotListener) : AbstractBotSubscriber(botListener) {

    private val log = logger<BotLogger>()

    override fun receiveUpdate(update: Update) {
        val message = update.message()
        log.info("Calling getMessage (${message.chat().id()},'${message.text()}')")
    }

}
