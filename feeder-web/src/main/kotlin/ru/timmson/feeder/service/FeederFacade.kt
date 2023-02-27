package ru.timmson.feeder.service

interface FeederFacade {

    fun sendStocksToOwner()

    fun sendStocksToChannel()
}