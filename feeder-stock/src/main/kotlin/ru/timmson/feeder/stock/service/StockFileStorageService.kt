package ru.timmson.feeder.stock.service

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.stereotype.Service
import ru.timmson.feeder.common.FileStorage
import ru.timmson.feeder.stock.dao.StockDAOException
import ru.timmson.feeder.stock.model.Indicator
import java.math.BigDecimal
import java.math.RoundingMode

@Service
open class StockFileStorageService(
    private val fileStorage: FileStorage,
    private val objectMapper: ObjectMapper
) {

    private val file = "stocks.json"

    fun getStockByTicker(ticker: String): Indicator {
        try {
            val price = BigDecimal(read()[ticker])

            return Indicator(ticker.lowercase(), price.setScale(2, RoundingMode.HALF_UP))
        } catch (e: Exception) {
            throw StockDAOException(e)
        }
    }

    fun setStock(indicator: Indicator) {
        try {
            val data = read().toMutableMap().apply { this[indicator.name] = indicator.price.toString() }

            fileStorage.write(file, objectMapper.writeValueAsString(data))
        } catch (e: Exception) {
            throw StockDAOException(e)
        }
    }

    private fun read(): Map<String, String> =
        objectMapper.readValue(fileStorage.read(file), object : TypeReference<Map<String, String>>() {})

}
