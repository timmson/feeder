package ru.timmson.feeder.stock.dao

import org.springframework.stereotype.Service
import ru.timmson.feeder.stock.model.Indicator
import ru.timmson.feeder.stock.service.StockFileStorageService

@Service
open class MainInfoDAO(
    private val centralBankAPI: CentralBankAPI,
    stockFileStorageService: StockFileStorageService
) : CachedStockDAO(
    stockFileStorageService = stockFileStorageService
) {

    override fun getStockByTicker(ticker: String): Indicator =
        putAndGet(centralBankAPI.getMainInfo()[ticker])
}
