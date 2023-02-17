package ru.timmson.feeder.moex.service

import ru.timmson.feeder.moex.model.Stock

interface StockService {

    fun findAll(): List<Stock>

}