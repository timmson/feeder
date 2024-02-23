package ru.timmson.feeder.service

import org.springframework.stereotype.Service
import ru.timmson.feeder.bot.BotService
import ru.timmson.feeder.bot.model.request.SendMessage
import ru.timmson.feeder.common.FeederConfig
import ru.timmson.feeder.common.logger
import ru.timmson.feeder.cv.CVRegistrar
import ru.timmson.feeder.cv.CVStore
import ru.timmson.feeder.cv.model.CVRegisterRequest
import ru.timmson.feeder.cv.model.Fields
import ru.timmson.feeder.stock.service.StockService

@Service
class FeederFacade(
    private val feederConfig: FeederConfig,
    private val stockService: StockService,
    private val cvRegistrar: CVRegistrar,
    private val cvStore: CVStore,
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

    fun registerCV(cvRequest: RegisterCVRequest) {
        log.info("Entering registerCV([${cvRequest.fileName}]) ...")

        val cv = cvRegistrar.parse(
            CVRegisterRequest(
                forwardedChatId = cvRequest.forwardedChatId,
                forwardedMessageId = cvRequest.forwardedMessageId,
                caption = cvRequest.caption,
                fileName = cvRequest.fileName
            )
        )

        val fields =
            Fields(
                name = cv.name,
                area = cv.area,
                title = cv.title,
                type = cv.type,
                date = cvRequest.forwardedMessagedDate,
                url = cv.url
            )

        cvStore.add(fields)

        botService.sendMessage(
            SendMessage(
                cvRequest.chatId,
                "<code>$fields</code>",
                true
            )
        )

        log.info("Leaving registerCV(...) = $cv")
    }
}
