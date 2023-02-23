package ru.timmson.feeder.bot.subriber

import com.pengrad.telegrambot.model.Update
import org.springframework.stereotype.Service
import ru.timmson.feeder.bot.BotListener
import ru.timmson.feeder.bot.BotService
import ru.timmson.feeder.common.FeederConfig
import ru.timmson.feeder.schedule.Schedule

@Service
class BotDispatcher(
    private val feederConfig: FeederConfig,
    private val botService: BotService,
    private val schedule: Schedule,
    botListener: BotListener
) : AbstractBotSubscriber(botListener), BotSubscriber {

    override fun receiveUpdate(update: Update) {
        val chantId = update.message().chat().id()
        if (chantId != feederConfig.ownerId.toLong()) {
            botService.sendMessage(chantId, "Sorry :(")
        }
        when (update.message().text()) {
            "/stock" -> schedule.sendStocksToOwner()
        }
    }
}