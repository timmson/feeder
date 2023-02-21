package ru.timmson.feeder

import jakarta.annotation.PostConstruct
import org.springframework.beans.factory.annotation.Value
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import ru.timmson.feeder.common.logger
import ru.timmson.feeder.stock.service.StockService

@Service
class Schedule(
    @Value("\${feeder.stock.schedule}") private val scheduleStock: String,
    private val stockService: StockService
) {

    private val log = logger<Schedule>()

    @PostConstruct
    fun init() {
        log.info("Schedule is $scheduleStock")
    }

    @Scheduled(cron = "\${feeder.stock.schedule}")
    fun fetchStocks() {
        log.info("Entering fetchStocks() ...")
        stockService.findAll().forEach { log.info(it.toString()) }
        log.info("Leaving fetchStocks() ...")
    }

}