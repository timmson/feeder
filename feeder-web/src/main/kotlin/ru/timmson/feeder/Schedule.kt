package ru.timmson.feeder

import jakarta.annotation.PostConstruct
import org.springframework.beans.factory.annotation.Value
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import ru.timmson.feeder.stock.service.StockService
import java.util.logging.Logger

@Service
class Schedule(
    @Value("\${feeder.schedule.stock}") val scheduleStock: String,
    private val stockService: StockService
) {

    private val log = Logger.getLogger(Schedule::class.java.toString())

    @PostConstruct
    fun init() {
        log.info("Schedule is $scheduleStock")
    }

    @Scheduled(cron = "\${feeder.schedule.stock}")
    fun fetchStocks() {
        log.info("Entering fetchStocks() ...")
        stockService.findAll().forEach { log.info(it.toString()) }
        log.info("Leaving fetchStocks() ...")
    }

}