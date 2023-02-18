package ru.timmson.feeder.stock.dao

import ru.timmson.feeder.stock.model.Stock

interface StockDAO {

    fun getStockByTicker(ticker: String): Stock

}
