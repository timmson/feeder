package ru.timmson.feeder.stock.service

import org.springframework.stereotype.Service
import ru.timmson.feeder.stock.dao.MarketWatchDAO
import ru.timmson.feeder.stock.dao.MoscowExchangeDAO
import ru.timmson.feeder.stock.model.Stock
import java.util.stream.Collectors

@Service
class StockServiceImpl(
    private val moscowExchangeDAO: MoscowExchangeDAO,
    private val marketWatchDAO: MarketWatchDAO
) : StockService {

    private val stocks = mapOf(
        "usd" to { moscowExchangeDAO.getStockByTicker("usd") },
        "imoex" to { moscowExchangeDAO.getStockByTicker("imoex") },
        "mrdec" to { moscowExchangeDAO.getStockByTicker("mredc") },
        "spx" to { marketWatchDAO.getStockByTicker("spx") },
        "shcomp" to { marketWatchDAO.getStockByTicker("shcomp") },
    )

    override fun findAll(): List<Stock> =
        stocks.values.parallelStream().map { it.invoke() }.collect(Collectors.toList())
}