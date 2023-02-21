package ru.timmson.feeder

import jakarta.annotation.PostConstruct
import org.springframework.beans.factory.annotation.Value
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import ru.timmson.feeder.bot.BotService
import ru.timmson.feeder.common.logger
import ru.timmson.feeder.stock.service.StockService

@Service
class Schedule(
    @Value("\${feeder.stock.schedule}") private val scheduleStock: String,
    private val stockService: StockService,
    private val botService: BotService
) {

    private val log = logger<Schedule>()

    @PostConstruct
    fun init() {
        log.info("Schedule is $scheduleStock")
    }

    @Scheduled(cron = "\${feeder.stock.schedule}")
    fun sendStocks() {
        log.info("Entering sendStocks() ...")

        val message = stockService.findAll().map {
            when (it.ticker) {
                "usd" -> "💰"
                "imoex" -> "🇷🇺"
                "mredc" -> "🏡"
                "spx" -> "🇺🇸"
                "shcomp" -> "🇨🇳"
                else -> ""
            } + it.price
        }.joinToString(", ")

        botService.sendMessage(message)

        log.info("Leaving sendStocks(...) = ")
    }

}