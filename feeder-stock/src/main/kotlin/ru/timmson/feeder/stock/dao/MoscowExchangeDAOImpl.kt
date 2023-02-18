package ru.timmson.feeder.stock.dao

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.stereotype.Service
import ru.timmson.feeder.stock.model.MEStock
import ru.timmson.feeder.stock.model.Stock
import java.math.BigDecimal
import java.math.RoundingMode
import java.util.logging.Logger

@Service
class MoscowExchangeDAOImpl(private val requester: Requester) : MoscowExchangeDAO {

    private val log = Logger.getLogger(MarketWatchDAOImpl::class.java.toString())

    private val tickers = mapOf(
        //"usd" to "https://iss.moex.com/iss/engines/currency/markets/selt/securities.jsonp?securities=CETS:USD000UTSTOM"
        "usd" to "https://iss.moex.com/iss/engines/currency/markets/selt/securities/USD000UTSTOM.json"
    )

    override fun getStockByTicker(ticker: String): Stock {
        try {
            val url = tickers["usd"] ?: ""

            log.info("Requesting $url ...")
            val response = requester.url(url)
            log.info("... done [length=${response.length}]")

            val meStock = getObjectMapper().readValue(response, MEStock::class.java)

            val index = meStock.securities.columns.indexOf("PREVPRICE")
            val price = BigDecimal(meStock.securities.data[0][index])

            return Stock(ticker.lowercase(), price.setScale(2, RoundingMode.HALF_UP))
        } catch (e: Exception) {
            throw StockDAOException(e)
        }
    }

    private fun getObjectMapper() =
        ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)

}