package ru.timmson.feeder.service

import org.springframework.stereotype.Service
import ru.timmson.feeder.bot.BotService
import ru.timmson.feeder.common.logger
import ru.timmson.feeder.stock.service.StockService

@Service
class FeederFacadeImpl(
    private val stockService: StockService,
    private val botService: BotService
) : FeederFacade {

    private val log = logger<FeederFacadeImpl>()

    override fun sendStocksToOwner() {
        log.info("Entering sendStocks() ...")

        val message = stockService.findAll().joinToString(", ") {
            when (it.ticker) {
                "usd" -> "💰"
                "imoex" -> "🇷🇺"
                "mredc" -> "🏡"
                "spx" -> "🇺🇸"
                "shcomp" -> "🇨🇳"
                else -> ""
            } + it.price
        }

        botService.sendMessageToOwner(message)

        log.info("Leaving sendStocks(...)")
    }
}