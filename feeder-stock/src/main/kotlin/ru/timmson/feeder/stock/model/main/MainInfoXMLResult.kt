package ru.timmson.feeder.stock.model.main

import com.fasterxml.jackson.annotation.JsonProperty

class MainInfoXMLResult {
    @JsonProperty("RegData")
    var regData: RegData? = null
}
