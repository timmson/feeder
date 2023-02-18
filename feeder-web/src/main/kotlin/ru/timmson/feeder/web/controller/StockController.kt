package ru.timmson.feeder.web.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import ru.timmson.feeder.stock.model.Stock
import ru.timmson.feeder.stock.service.StockService

@RestController
class StockController(private val stockService: StockService) {

    @GetMapping("/stock/all")
    fun getAllStocks(): List<Stock> = stockService.findAll()

}