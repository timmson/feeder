package ru.timmson.feeder.stock.model.main

import com.fasterxml.jackson.annotation.JsonProperty

class MainInfoXMLResponse {
    @JsonProperty("MainInfoXMLResult")
    var mainInfoXMLResult: MainInfoXMLResult? = null
}

