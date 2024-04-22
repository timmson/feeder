package ru.timmson.feeder.stock.service

import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.runBlocking
import org.springframework.stereotype.Service
import ru.timmson.feeder.common.logger
import ru.timmson.feeder.stock.dao.CentralBankDAO
import ru.timmson.feeder.stock.dao.MoscowExchangeDAO
import ru.timmson.feeder.stock.dao.StockDAO
import ru.timmson.feeder.stock.dao.StockStorageDAO
import ru.timmson.feeder.stock.model.Indicator
import java.math.BigDecimal

@Service
class IndicatorService(
    private val centralBankDAO: CentralBankDAO,
    moscowExchangeDAO: MoscowExchangeDAO,
    stockStorageDAO: StockStorageDAO,
    private val stockFileStorageService: StockFileStorageService
) {

    private val log = logger<IndicatorService>()

    private val stocks = mapOf(
        "usd" to moscowExchangeDAO,
        "imoex" to moscowExchangeDAO,
        "mredc" to moscowExchangeDAO,
        "spx" to stockStorageDAO,
        "shcomp" to stockStorageDAO
    )

    fun findAll(): List<Indicator> {
        val stocks = runBlocking {
            stocks.entries.asFlow().transform { emit(getStock(it)) }.toList()
        }
        return stocks + getMainInfo()
    }

    fun put(stock: Indicator) =
        stockFileStorageService.setStock(stock)

    private fun getMainInfo(): List<Indicator> {
        var keyRate = BigDecimal.ZERO
        var inflation = BigDecimal.ZERO
        try {
            centralBankDAO.getMainInfo().run {
                keyRate = this.keyRate
                inflation = this.inflation
            }
        } catch (e: Exception) {
            log.severe("MainInfo is not received: $e")
        }
        return listOf(
            Indicator("keyRate", keyRate),
            Indicator("inflation", inflation)
        )
    }

    private fun getStock(it: Map.Entry<String, StockDAO>): Indicator {
        try {
            return it.value.getStockByTicker(it.key)
        } catch (e: Exception) {
            log.severe("Stock ${it.key} is not received: $e")
        }
        return Indicator(it.key, BigDecimal.ZERO)
    }

}
