package ru.timmson.feeder.stock.dao

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.stereotype.Service
import ru.timmson.feeder.common.FileStorage
import ru.timmson.feeder.stock.model.Indicator
import java.math.BigDecimal
import java.math.RoundingMode

@Service
open class CommonStorageDAO(
    private val fileStorage: FileStorage,
    private val objectMapper: ObjectMapper
) : CachedStockDAO() {

    override fun getStockByTicker(ticker: String): Indicator {
        try {
            val data = objectMapper.readValue(fileStorage.read("stocks.json"), object : TypeReference<Map<String, String>>() {})

            val price = BigDecimal(data[ticker])

            return Indicator(ticker.lowercase(), price.setScale(2, RoundingMode.HALF_UP))
        } catch (e: Exception) {
            throw StockDAOException(e)
        }
    }

}
