package ru.timmson.feeder.stock.dao

interface Requester {

    fun url(url: String): String

}
