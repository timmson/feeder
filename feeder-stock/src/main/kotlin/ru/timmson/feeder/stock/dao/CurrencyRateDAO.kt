package ru.timmson.feeder.stock.dao

import org.springframework.stereotype.Service
import ru.timmson.feeder.stock.model.Indicator
import java.time.LocalDate

@Service
open class CurrencyRateDAO(
    private val centralBankAPI: CentralBankAPI
) : CachedStockDAO() {

    override fun getStockByTicker(ticker: String): Indicator =
        centralBankAPI.getCursInfo(LocalDate.now())[ticker]!!

}
