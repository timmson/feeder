package ru.timmson.feeder.stock.model

data class StockConfig(
    val ticker: String,
    val url: String,
    val priceField: String,
    val scale: Int = 2
)