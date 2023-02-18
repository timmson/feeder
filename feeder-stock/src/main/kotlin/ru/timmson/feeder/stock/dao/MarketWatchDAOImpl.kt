package ru.timmson.feeder.stock.dao

import org.jsoup.Jsoup
import org.springframework.stereotype.Service
import ru.timmson.feeder.stock.model.Stock
import java.math.BigDecimal
import java.math.RoundingMode
import java.util.logging.Logger

@Service
class MarketWatchDAOImpl(private val requester: Requester) : MarketWatchDAO {

    private val log = Logger.getLogger(MarketWatchDAOImpl::class.java.toString())

    override fun getStockByTicker(ticker: String): Stock {
        try {
            val type = "index"

            val realTicker = when {
                ticker.contentEquals("shcomp") -> "shcomp?countrycode=cn"
                else -> ticker.lowercase()
            }

            val url = "https://www.marketwatch.com/investing/${type}/${realTicker}"

            log.info("Requesting $url ...")
            val response = requester.url(url)
            log.info("... done [length=${response.length}]")

            val doc = Jsoup.parse(response)
            val price = BigDecimal(doc.select("meta[name=\"price\"]").attr("content").replace(",", ""))

            return Stock(ticker.lowercase(), price.setScale(2, RoundingMode.HALF_UP))
        } catch (e: Exception) {
            throw StockDAOException(e)
        }
    }

}