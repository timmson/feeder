package ru.timmson.feeder.stock.dao

import org.springframework.stereotype.Service
import ru.timmson.feeder.stock.model.Indicator

@Service
open class MainInfoDAO(
    private val centralBankAPI: CentralBankAPI
) : CachedStockDAO() {

    override fun getStockByTicker(ticker: String): Indicator =
        centralBankAPI.getMainInfo()[ticker]!!
}
