package ru.timmson.feeder.stock.service

import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.runBlocking
import org.springframework.stereotype.Service
import ru.timmson.feeder.stock.dao.CentralBankDAO
import ru.timmson.feeder.stock.dao.MarketWatchDAO
import ru.timmson.feeder.stock.dao.MoscowExchangeDAO
import ru.timmson.feeder.stock.model.Indicator

@Service
class IndicatorService(
    private val centralBankDAO: CentralBankDAO,
    moscowExchangeDAO: MoscowExchangeDAO,
    marketWatchDAO: MarketWatchDAO
) {

    private val stocks = mapOf(
        "usd" to moscowExchangeDAO,
        "imoex" to moscowExchangeDAO,
        "mredc" to moscowExchangeDAO,
        "spx" to marketWatchDAO,
        "shcomp" to marketWatchDAO
    )

    fun findAll(): List<Indicator> {
        val stocks = runBlocking {
            stocks.entries.asFlow().transform { emit(it.value.getStockByTicker(it.key)) }.toList()
        }

        val mainInfo = centralBankDAO.getMainInfo().run {
            listOf(
                Indicator("keyRate", keyRate),
                Indicator("inflation", inflation)
            )
        }

        return stocks + mainInfo
    }

}
