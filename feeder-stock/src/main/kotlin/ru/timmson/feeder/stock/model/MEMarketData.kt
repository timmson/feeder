package ru.timmson.feeder.stock.model

class MEMarketData {
    lateinit var columns: List<String>
    lateinit var data: List<List<String?>>

    override fun toString(): String {
        return "MESecurities(columns=$columns, data=$data)"
    }

}