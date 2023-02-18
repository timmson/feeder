package ru.timmson.feeder.stock.dao

interface Requester {

    fun fetch(url: String): String

}
