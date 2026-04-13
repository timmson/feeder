package ru.timmson.feeder.web

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.timmson.feeder.stock.model.Indicator
import ru.timmson.feeder.stock.service.IndicatorService

@RestController
@RequestMapping("/api")
class StockController(
    private val indicatorService: IndicatorService
) {

    @GetMapping("/stocks")
    fun getStocks(): List<Indicator> = indicatorService.findAll()
}
