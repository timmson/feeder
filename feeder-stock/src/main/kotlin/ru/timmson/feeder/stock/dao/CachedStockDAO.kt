package ru.timmson.feeder.stock.dao

import org.springframework.cache.annotation.CacheConfig
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import ru.timmson.feeder.stock.model.Stock

@Service
@CacheConfig(cacheNames = ["tickers"])
abstract class CachedStockDAO : StockDAO {

    @Cacheable
    abstract override fun getStockByTicker(ticker: String): Stock
}
