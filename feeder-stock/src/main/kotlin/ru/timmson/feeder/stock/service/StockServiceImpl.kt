package ru.timmson.feeder.stock.service

import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.runBlocking
import org.springframework.stereotype.Service
import ru.timmson.feeder.stock.dao.MarketWatchDAO
import ru.timmson.feeder.stock.dao.MoscowExchangeDAO
import ru.timmson.feeder.stock.dao.StockDAO
import ru.timmson.feeder.stock.model.Stock
import java.util.logging.Logger

@Service
class StockServiceImpl(
    moscowExchangeDAO: MoscowExchangeDAO,
    marketWatchDAO: MarketWatchDAO
) : StockService {

    private val log = Logger.getLogger(StockServiceImpl::class.toString())

    private val stocks = mapOf(
        "usd" to moscowExchangeDAO,
        "imoex" to moscowExchangeDAO,
        "mredc" to moscowExchangeDAO,
        "spx" to marketWatchDAO,
        "shcomp" to marketWatchDAO
    )

    override fun findAll(): List<Stock> = runBlocking {
        stocks.entries.asFlow().transform { emit(getStock(it)) }.toList()
    }

    fun getTickers() = stocks.keys

    private fun getStock(it: Map.Entry<String, StockDAO>) =
        try {
            it.value.getStockByTicker(it.key)
        } catch (e: Exception) {
            log.severe("Stock ${it.key} is not received: $e")
            Stock(it.key)
        }

}