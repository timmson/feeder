package ru.timmson.feeder.stock.model

import java.math.BigDecimal

data class MainInfo(
    val keyRate: BigDecimal = BigDecimal.ZERO,
    val inflation: BigDecimal = BigDecimal.ZERO
)
