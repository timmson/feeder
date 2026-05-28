package ru.timmson.feeder.stock.service

import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.runBlocking
import org.springframework.cache.annotation.CacheEvict
import org.springframework.stereotype.Service
import ru.timmson.feeder.common.logger
import ru.timmson.feeder.stock.dao.*
import ru.timmson.feeder.stock.model.Indicator

@Service
open class IndicatorService(
    currencyRateDAO: CurrencyRateDAO,
    moscowExchangeDAO: MoscowExchangeDAO,
    mainInfoDAO: MainInfoDAO,
    private val stockStorageDAO: StockStorageDAO,
    private val stockFileStorageService: StockFileStorageService
) {

    private val log = logger<IndicatorService>()

    private val daos = mapOf(
        listOf("usd", "eur") to currencyRateDAO,
        listOf("imoex") to moscowExchangeDAO,
        listOf("mredc") to moscowExchangeDAO,
        listOf("keyRate", "inflation") to mainInfoDAO,
    )

    fun findAll(): List<Indicator> = runBlocking {
        daos.entries.asFlow().transform { emit(getStocks(it)) }.toList().flatten()
    }

    @CacheEvict(cacheNames = ["tickers", "mainInfo", "cursInfo"], allEntries = true)
    open fun refreshAll() {
        log.info("Cache cleared, refreshing all stock data")
    }

    fun put(stock: Indicator) = stockFileStorageService.setStock(stock)

    private fun getStocks(entry: Map.Entry<List<String>, StockDAO>): List<Indicator> =
        entry.key.map { getStock(it, entry.value) }

    private fun getStock(ticker: String, stockDAO: StockDAO): Indicator {
        try {
            return stockDAO.getStockByTicker(ticker)
        } catch (e: Exception) {
            log.severe("Stock $ticker has not been received: $e")
        }
        return stockStorageDAO.getStockByTicker(ticker)
    }


}
