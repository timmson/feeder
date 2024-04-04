package ru.timmson.feeder.service

import org.springframework.stereotype.Service
import ru.timmson.feeder.bot.BotService
import ru.timmson.feeder.common.FeederConfig
import ru.timmson.feeder.common.logger
import ru.timmson.feeder.cv.CVRegistrar
import ru.timmson.feeder.cv.CVStore
import ru.timmson.feeder.cv.model.CVRegisterRequest
import ru.timmson.feeder.cv.model.Fields
import ru.timmson.feeder.stock.service.IndicatorService
import java.math.BigDecimal

@Service
class FeederFacade(
    private val feederConfig: FeederConfig,
    private val indicatorService: IndicatorService,
    private val cvRegistrar: CVRegistrar,
    private val cvStore: CVStore,
    private val botService: BotService
) {

    private val log = logger<FeederFacade>()

    private val stocks = mapOf(
        "usd" to "💰 Курс USD: <b>%.2f руб.</b>",
        "imoex" to "🇷🇺 Индекс Мосбиржи: <b>%.0f</b>",
        "spx" to "🇺🇸 S&P 500 Index: <b>%.0f</b>",
        "shcomp" to "🇨🇳 Shanghai Composite Index: <b>%.0f</b>",
        "keyRate" to "🗝 Ключевая ставка: <b>%.2f%%</b>",
        "inflation" to "🎈 Офиц. инфляция: <b>%.2f%%</b>",
        "mredc" to "🏡 Индекс Мос. Недвиж. ДомКлик: <b>%.0f</b>"
    )

    fun sendStocksToOwner() =
        sendStocks { message: String -> botService.sendMessageToOwner(message) }

    fun sendStocksToChannel() =
        sendStocks { message: String -> botService.sendMessage(feederConfig.stockChannelId, message) }


    private fun sendStocks(send: (String) -> Unit) {
        log.info("Entering sendStocks() ...")

        val message = indicatorService.findAll().filter { it.price != BigDecimal.ZERO }.joinToString("\n") {
            String.format(stocks.getOrDefault(it.name, ""), it.price)
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

        botService.sendMessage(cvRequest.chatId, "<code>$fields</code>")

        log.info("Leaving registerCV(...) = $cv")
    }
}
