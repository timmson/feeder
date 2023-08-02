package ru.timmson.feeder.bot.subscriber

import com.pengrad.telegrambot.model.Update
import org.springframework.stereotype.Service
import ru.timmson.feeder.bot.BotListener
import ru.timmson.feeder.bot.BotService
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
        val chantId = update.message().chat().id()

        if (chantId != feederConfig.ownerId.toLong()) {
            botService.sendMessage(chantId, "Sorry :(")
        } else {
            when {
                update.message().text() != null -> {
                    update.message().text().let {
                        when {
                            it.startsWith("/stock") -> feederFacade.sendStocksToOwner()
                            it.startsWith("/w") -> feederFacade.sendMeaningAndTranslation(
                                chantId.toString(),
                                it.replace("/w", "").trim()
                            )
                        }
                    }
                }

                update.message().document() != null -> {

                    try {
                        update.message().let {
                            feederFacade.registerCV(
                                RegisterCVRequest(
                                    chantId.toString(),
                                    it.forwardFromMessageId(),
                                    it.forwardDate(),
                                    it.caption(),
                                    it.document().fileName()
                                )
                            )
                        }
                    } catch (e: Exception) {
                        botService.sendMessage(chantId, "This document has incorrect fields: ${e.message}")
                    }
                }

                else -> {
                    botService.sendMessage(chantId, "This message does not contain any of known formats ;(")
                }
            }
        }
    }
}
