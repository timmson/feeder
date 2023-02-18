package ru.timmson.feeder.stock.service

import org.springframework.stereotype.Service
import ru.timmson.feeder.stock.dao.MarketWatchDAO
import ru.timmson.feeder.stock.model.Stock
import java.math.BigDecimal
import java.math.RoundingMode

/**
 * TODO Add async call and exception handling
 */

@Service
class StockServiceImpl(
    private val marketWatchDAO: MarketWatchDAO
) : StockService {

    private val stocks = mapOf(
        "usd" to { Stock("usd", BigDecimal(76.76).setScale(2, RoundingMode.HALF_UP)) },
        "imoex" to { Stock("imoex", BigDecimal(2000.00).setScale(2, RoundingMode.HALF_UP)) },
        "mrdec" to { Stock("mrdec", BigDecimal(250.76).setScale(2, RoundingMode.HALF_UP)) },
        "spx" to { marketWatchDAO.getStockByTicker("spx") },
        "shcomp" to { marketWatchDAO.getStockByTicker("shcomp") },
    )

    override fun findAll(): List<Stock> = stocks.values.map { it.invoke() }
}