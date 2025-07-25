package ru.timmson.feeder.service

import org.springframework.stereotype.Service
import ru.timmson.feeder.bot.BotService
import ru.timmson.feeder.common.FeederConfig
import ru.timmson.feeder.common.logger
import ru.timmson.feeder.cv.CVRegistrar
import ru.timmson.feeder.cv.CVStore
import ru.timmson.feeder.cv.model.CVRegisterRequest
import ru.timmson.feeder.cv.model.Fields
import ru.timmson.feeder.stock.model.Indicator
import ru.timmson.feeder.stock.service.IndicatorService
import java.math.BigDecimal

@Service
class FeederFacade(
    private val cvStore: CVStore,
    private val botService: BotService,
    private val cvRegistrar: CVRegistrar,
    private val feederConfig: FeederConfig,
    private val indicatorService: IndicatorService,
    private val cvEstimationService: CVEstimationService,
) {

    private val log = logger<FeederFacade>()

    private val stocks = mapOf(
        "usd" to "💵 <a href=\"https://www.cbr.ru/currency_base/daily/\">Курс USD</a>: <b>%.2f руб.</b>",
        "eur" to "💶 <a href=\"https://www.cbr.ru/currency_base/daily/\">Курс EUR</a>: <b>%.2f руб.</b>",
        "imoex" to "🇷🇺 <a href=\"https://www.moex.com/ru/index/IMOEX\">Индекс Мосбиржи</a>: <b>%.0f</b>",
        "keyRate" to "🗝 <a href=\"https://www.cbr.ru/hd_base/keyrate/\">Ключевая ставка</a>: <b>%.2f%%</b>",
        "inflation" to "🎈 <a href=\"https://www.cbr.ru/hd_base/infl/\">Офиц. инфляция</a>: <b>%.2f%%</b>",
        "mredc" to "🏡 <a href=\"https://www.moex.com/ru/index/MREDC\">Стоимость м2 в Москве</a>: <b>%.0f руб.</b>"
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

    fun putStock(message: String) {
        log.info("Entering putSock(message=$message) ...")

        val data = message.split(" ")
        indicatorService.put(Indicator(data[1], BigDecimal(data[2])))

        botService.sendMessageToOwner("ok")

        log.info("Leaving putStock(...)")
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

        if (feederConfig.isSpreadSheatEnabled) {
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
            botService.sendMessage(cvRequest.chatId, "Кандидат:\n<code>$fields</code>")
        }

        if (feederConfig.isYandexEnabled) {
            val cvFile = botService.downloadFile(cvRequest.fileId)
            val estimation = cvEstimationService.estimate(cv.title.lowercase(), cvFile)
            botService.sendMessage(cvRequest.chatId, "Оценка кандидата ${cv.name} на позицию ${cv.title.lowercase()}:\n\n<i>$estimation</i>")
        }

        log.info("Leaving registerCV(...) = $cv")
    }
}
