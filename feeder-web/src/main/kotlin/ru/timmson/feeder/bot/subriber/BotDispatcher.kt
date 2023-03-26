package ru.timmson.feeder.bot.subriber

import com.pengrad.telegrambot.model.Update
import org.springframework.stereotype.Service
import ru.timmson.feeder.bot.BotListener
import ru.timmson.feeder.bot.BotService
import ru.timmson.feeder.common.FeederConfig
import ru.timmson.feeder.service.FeederFacade

@Service
class BotDispatcher(
    private val feederConfig: FeederConfig,
    private val botService: BotService,
    private val feederFacade: FeederFacade,
    botListener: BotListener
) : AbstractBotSubscriber(botListener) {

    override fun receiveUpdate(update: Update) {
        val chantId = update.message().chat().id()

        if (chantId != feederConfig.ownerId.toLong()) {
            botService.sendMessage(chantId, "Sorry :(")
        } else {
            update.message().text().let {
                when {
                    it.startsWith("/stock") -> feederFacade.sendStocksToOwner()
                    it.startsWith("/w") -> feederFacade.sendMeaningToOwner(it.replace("/w", "").trim())
                }
            }
        }
    }
}
