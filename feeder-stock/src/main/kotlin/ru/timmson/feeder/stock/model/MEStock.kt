package ru.timmson.feeder.stock.model

class MEStock {
    lateinit var securities: MESecurities

    override fun toString(): String {
        return "MEStock(securities=$securities)"
    }
}