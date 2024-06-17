package ru.timmson.feeder.stock.model.main

import com.fasterxml.jackson.annotation.JsonProperty

class RegData {
    @JsonProperty("keyRate")
    var keyRate: Double? = null

    @JsonProperty("Inflation")
    var inflation: Double? = null

    @JsonProperty("stavka_ref")
    var refinanceRate: Double? = null

    @JsonProperty("GoldBaks")
    var goldBaks: Double? = null
}
