package ru.timmson.feeder.stock.service

import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.runBlocking
import org.springframework.stereotype.Service
import ru.timmson.feeder.common.logger
import ru.timmson.feeder.stock.dao.*
import ru.timmson.feeder.stock.model.Indicator
import java.math.BigDecimal

@Service
class IndicatorService(
    currencyRateDAO: CurrencyRateDAO,
    moscowExchangeDAO: MoscowExchangeDAO,
    stockStorageDAO: StockStorageDAO,
    mainInfoDAO: MainInfoDAO,
    private val stockFileStorageService: StockFileStorageService
) {

    private val log = logger<IndicatorService>()

    private val stocks = mapOf(
        listOf("usd", "eur") to currencyRateDAO,
        listOf("imoex") to moscowExchangeDAO,
        listOf("mredc") to moscowExchangeDAO,
        listOf("spx", "shcomp") to stockStorageDAO,
        listOf("keyRate", "inflation") to mainInfoDAO,
    )

    fun findAll(): List<Indicator> = runBlocking {
        stocks.entries.asFlow().transform { emit(getStocks(it)) }.toList().flatten()
    }

    fun put(stock: Indicator) = stockFileStorageService.setStock(stock)

    private fun getStocks(stocks: Map.Entry<List<String>, StockDAO>): List<Indicator> =
        stocks.key.map { getStock(it, stocks.value) }

    private fun getStock(ticker: String, stockDAO: StockDAO): Indicator {
        try {
            return stockDAO.getStockByTicker(ticker)
        } catch (e: Exception) {
            log.severe("Stock $ticker has not been received: $e")
        }
        return Indicator(ticker, BigDecimal.ZERO)
    }


}
