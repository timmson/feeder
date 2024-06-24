package ru.timmson.feeder.stock.dao

import org.springframework.cache.annotation.CacheConfig
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import ru.timmson.feeder.stock.model.Indicator
import ru.timmson.feeder.stock.service.StockFileStorageService

@Service
@CacheConfig(cacheNames = ["tickers"])
abstract class CachedStockDAO(
    private val stockFileStorageService: StockFileStorageService
) : StockDAO {

    protected fun putAndGet(indicator: Indicator?): Indicator {
        stockFileStorageService.setStock(indicator!!)
        return indicator
    }

    @Cacheable
    abstract override fun getStockByTicker(ticker: String): Indicator
}
