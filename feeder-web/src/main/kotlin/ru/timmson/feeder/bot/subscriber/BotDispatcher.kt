package ru.timmson.feeder.bot.subscriber

import com.pengrad.telegrambot.model.Update
import org.springframework.stereotype.Service
import ru.timmson.feeder.bot.BotListener
import ru.timmson.feeder.bot.BotService
import ru.timmson.feeder.common.Date
import ru.timmson.feeder.common.FeederConfig
import ru.timmson.feeder.service.FeederFacade
import ru.timmson.feeder.service.RegisterCVRequest

@Service
class BotDispatcher(
    private val feederConfig: FeederConfig,
    private val botService: BotService,
    private val feederFacade: FeederFacade,
    botListener: BotListener
) : AbstractBotSubscriber(botListener) {

    override fun receiveUpdate(update: Update) {
        val chatId = update.message().chat().id()

        when {
            feederConfig.ownerId.toLong() != chatId -> botService.sendMessage(chatId, "Sorry :(")
            update.message().text() != null -> onMessage(update)
            update.message().document() != null -> onDocument(update)
            else -> botService.sendMessage(chatId, "This message does not contain any of known formats ;(")
        }
    }

    private fun onMessage(update: Update) {
        val chatId = update.message().chat().id()

        update.message().text().let {
            when {
                it.startsWith("/stock") -> feederFacade.sendStocksToOwner()
                it.startsWith("/w") -> feederFacade.sendMeaningAndTranslation(chatId.toString(), it.replace("/w", "").trim())
            }
        }
    }

    private fun onDocument(update: Update) {
        val chatId = update.message().chat().id()

        try {
            update.message().let {
                feederFacade.registerCV(
                    RegisterCVRequest(
                        chatId,
                        if (it.forwardFromChat() != null && it.forwardFromChat().id() != null) it.forwardFromChat().id() else 0,
                        it.forwardFromMessageId() ?: 0,
                        if (it.forwardDate() != null) Date.format(it.forwardDate().toLong()) else Date.today,
                        it.caption(),
                        it.document().fileName()
                    )
                )
            }
        } catch (e: Exception) {
            botService.sendMessage(chatId, "This document has an incorrect fields: ${e.message}")
        }
    }
}
