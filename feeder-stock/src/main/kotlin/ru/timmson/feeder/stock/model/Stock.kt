package ru.timmson.feeder.stock.model

import java.math.BigDecimal

data class Stock(
    val ticker: String,
    val price: BigDecimal = BigDecimal.ZERO
)