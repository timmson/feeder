package ru.timmson.feeder.moex.service

import org.springframework.stereotype.Service
import ru.timmson.feeder.moex.model.Stock
import java.math.BigDecimal

@Service
class StockServiceImpl : StockService {

    override fun findAll(): List<Stock> {
        return listOf(
            Stock().apply {
                ticker = "USD"
                price = BigDecimal(76.76)
            },
            Stock().apply {
                ticker = "S&P"
                price = BigDecimal(4000)
            }
        )
    }
}