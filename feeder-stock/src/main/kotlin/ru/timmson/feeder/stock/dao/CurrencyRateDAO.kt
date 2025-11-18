package ru.timmson.feeder.stock.dao

import org.springframework.stereotype.Service
import ru.timmson.feeder.stock.model.Indicator
import ru.timmson.feeder.stock.service.StockFileStorageService
import java.time.LocalDate

@Service
open class CurrencyRateDAO(
    private val centralBankAPI: CentralBankAPI,
    stockFileStorageService: StockFileStorageService
) : CachedStockDAO(
    stockFileStorageService = stockFileStorageService
) {

    override fun getStockByTicker(ticker: String): Indicator =
        putAndGet(centralBankAPI.getCursInfo(LocalDate.now().plusDays(1))[ticker])

}
