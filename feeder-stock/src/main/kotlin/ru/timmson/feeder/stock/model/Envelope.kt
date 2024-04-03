package ru.timmson.feeder.stock.model

import com.fasterxml.jackson.annotation.JsonProperty

class Envelope {
    @JsonProperty("Body")
    var body: Body? = null
}

class Body {
    @JsonProperty("MainInfoXMLResponse")
    var mainInfoXMLResponse: MainInfoXMLResponse? = null
}

class MainInfoXMLResponse {
    @JsonProperty("MainInfoXMLResult")
    var mainInfoXMLResult: MainInfoXMLResult? = null
}

class MainInfoXMLResult {
    @JsonProperty("RegData")
    var regData: RegData? = null
}

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
