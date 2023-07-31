package ru.timmson.feeder.service

import org.springframework.stereotype.Service
import ru.timmson.feeder.bot.BotService
import ru.timmson.feeder.bot.model.request.SendMessage
import ru.timmson.feeder.common.Date
import ru.timmson.feeder.common.FeederConfig
import ru.timmson.feeder.common.logger
import ru.timmson.feeder.cv.CVRegisterRequest
import ru.timmson.feeder.cv.CVRegistrar
import ru.timmson.feeder.lingua.LinguaService
import ru.timmson.feeder.stock.service.StockService

@Service
class FeederFacade(
    private val feederConfig: FeederConfig,
    private val stockService: StockService,
    private val linguaService: LinguaService,
    private val cvRegistrar: CVRegistrar,
    private val printService: PrintService,
    private val botService: BotService
) {

    private val log = logger<FeederFacade>()

    private val stocks = mapOf(
        "usd" to "💰",
        "imoex" to "🇷🇺",
        "mredc" to "🏡",
        "spx" to "🇺🇸",
        "shcomp" to "🇨🇳"
    )

    fun sendStocksToOwner() =
        sendStocks { message: String -> botService.sendMessageToOwner(message) }

    fun sendStocksToChannel() =
        sendStocks { message: String -> botService.sendMessage(feederConfig.stockChannelId, message) }


    private fun sendStocks(send: (String) -> Unit) {
        log.info("Entering sendStocks() ...")

        val message = stockService.findAll().joinToString(", ") {
            stocks.getOrDefault(it.ticker, "") + it.price
        }

        send(message)

        log.info("Leaving sendStocks(...)")
    }

    fun sendMeaningAndTranslation(chatId: String, word: String) {
        log.info("Entering sendMeaningToOwner([$word]) ...")

        linguaService.explain(word).let { response ->
            response.meanings.joinToString("\n\n") { "- ${it.value}" }.let { message ->
                botService.sendMessage(chatId, "${message}\n\n${response.url}")
            }
        }

        linguaService.translate(word).let { response ->
            response.translate.joinToString("\n\n") { "- ${it.value}" }.let { message ->
                botService.sendMessage(chatId, "${message}\n\n${response.url}")
            }
        }

        log.info("Leaving sendMeaningToOwner(...)")
    }

    fun registerCV(cvRequest: RegisterCVRequest) {
        log.info("Entering registerCV([${cvRequest.fileName}]) ...")

        val cv = cvRegistrar.parse(CVRegisterRequest(caption = cvRequest.caption, fileName = cvRequest.fileName))
        val text = printService.printCV(cv, Date.format(cvRequest.forwardedMessagedTimeStamp.toLong()))

        botService.sendMessage(
            SendMessage(
                cvRequest.chatId,
                "<code>$text</code>\n\n<code>${feederConfig.cvChannelUrl}${cvRequest.forwardedMessageId}</code>",
                true
            )
        )

        log.info("Leaving registerCV(...) = $cv")
    }
}
