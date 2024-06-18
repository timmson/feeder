package ru.timmson.feeder.stock.dao

import ru.timmson.feeder.stock.model.Indicator

interface StockDAO {

    @Throws(StockDAOException::class)
    fun getStockByTicker(ticker: String): Indicator

}
