package ru.timmson.feeder.stock.dao

import org.jsoup.Jsoup
import org.springframework.stereotype.Service
import ru.timmson.feeder.stock.model.Indicator
import java.math.BigDecimal
import java.math.RoundingMode

@Service
open class MarketWatchDAO(private val requester: Requester) : CachedStockDAO() {

    override fun getStockByTicker(ticker: String): Indicator {
        try {
            val type = "index"

            val realTicker = when {
                ticker.contentEquals("shcomp") -> "shcomp?countrycode=cn"
                else -> ticker.lowercase()
            }

            val url = "https://www.marketwatch.com/investing/${type}/${realTicker}"

            val response = requester.get(url)
            val doc = Jsoup.parse(response)

            val price = BigDecimal(doc.select("meta[name=\"price\"]").attr("content").replace(",", ""))

            return Indicator(ticker.lowercase(), price.setScale(2, RoundingMode.HALF_UP))
        } catch (e: Exception) {
            throw StockDAOException(e)
        }
    }

}
