package ru.timmson.feeder.stock.model

class MEStock {
    lateinit var marketdata: MEMarketData

    override fun toString(): String {
        return "MEStock(securities=$marketdata)"
    }
}