package ru.timmson.feeder.stock.dao

import ru.timmson.feeder.stock.model.Indicator

interface StockDAO {

    fun getStockByTicker(ticker: String): Indicator

}
