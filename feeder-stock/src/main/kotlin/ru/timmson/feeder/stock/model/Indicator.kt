package ru.timmson.feeder.stock.model

import java.math.BigDecimal

data class Indicator(
    val name: String,
    val price: BigDecimal = BigDecimal.ZERO
)
