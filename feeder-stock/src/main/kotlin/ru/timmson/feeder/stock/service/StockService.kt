package ru.timmson.feeder.stock.service

import ru.timmson.feeder.stock.model.Stock

interface StockService {

    fun findAll(): List<Stock>

}