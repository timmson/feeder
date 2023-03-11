package ru.timmson.feeder.stock.service

import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.runBlocking
import org.springframework.stereotype.Service
import ru.timmson.feeder.common.logger
import ru.timmson.feeder.stock.dao.MarketWatchDAO
import ru.timmson.feeder.stock.dao.MoscowExchangeDAO
import ru.timmson.feeder.stock.dao.StockDAO
import ru.timmson.feeder.stock.model.Stock

@Service
class StockServiceImpl(
    moscowExchangeDAO: MoscowExchangeDAO,
    marketWatchDAO: MarketWatchDAO
) : StockService {

    private val log = logger<StockServiceImpl>()

    private val stocks = mapOf(
        "usd" to moscowExchangeDAO,
        "imoex" to moscowExchangeDAO,
        "mredc" to moscowExchangeDAO,
        "spx" to marketWatchDAO,
        "shcomp" to marketWatchDAO
    )

    private val cache = mutableMapOf<String, Stock>()

    override fun findAll(): List<Stock> = runBlocking {
        stocks.entries.asFlow().transform { emit(getStock(it)) }.toList()
    }

    override fun resetCache() {
        cache.clear()
    }

    fun getTickers() = stocks.keys

    private fun getStock(it: Map.Entry<String, StockDAO>): Stock {
        try {
            cache[it.key] = it.value.getStockByTicker(it.key)
        } catch (e: Exception) {
            log.severe("Stock ${it.key} is not received: $e")
        }
        return cache[it.key] ?: Stock(it.key)
    }

}
