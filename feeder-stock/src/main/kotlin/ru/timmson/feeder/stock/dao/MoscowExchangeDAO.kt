package ru.timmson.feeder.stock.dao

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.stereotype.Service
import ru.timmson.feeder.stock.model.MEStock
import ru.timmson.feeder.stock.model.Stock
import ru.timmson.feeder.stock.model.StockConfig
import java.math.BigDecimal
import java.math.RoundingMode

@Service
open class MoscowExchangeDAO(
    private val requester: Requester,
    private val objectMapper: ObjectMapper
) : CachedStockDAO() {

    private val stockConfigs = mapOf(
        "usd" to StockConfig(
            "usd",
            "https://iss.moex.com/iss/engines/currency/markets/selt/securities.jsonp?securities=CETS:USD000UTSTOM",
            //"usd" to "https://iss.moex.com/iss/engines/currency/markets/selt/securities/USD000UTSTOM.json",
            "LAST"
        ),
        "imoex" to StockConfig(
            "imoex",
            "https://iss.moex.com/iss/engines/stock/markets/index/securities/IMOEX.json",
            "LASTVALUE"
        ),
        "mredc" to StockConfig(
            "mredc",
            "https://iss.moex.com/iss/engines/stock/markets/index/securities/MREDC.json",
            "LASTVALUE",
            0
        )
    )

    override fun getStockByTicker(ticker: String): Stock {
        try {
            val stockConfig = stockConfigs[ticker] ?: throw Exception("Ticker \"$ticker\" is not known")

            val response = requester.fetch(stockConfig.url)
            val meStock = objectMapper.readValue(response, MEStock::class.java)

            val index = meStock.marketdata.columns.indexOf(stockConfig.priceField)
            val price = BigDecimal(meStock.marketdata.data[0][index])
                .setScale(stockConfig.scale, RoundingMode.HALF_UP)

            return Stock(ticker.lowercase(), price)
        } catch (e: Exception) {
            throw StockDAOException(e)
        }
    }

}
