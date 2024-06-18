package ru.timmson.feeder.stock.model.curs

import com.fasterxml.jackson.annotation.JsonProperty

class ValuteCursOnDate() {

    @JsonProperty("Vname")
    var name: String? = null

    @JsonProperty("Vnom")
    var number: String? = null

    @JsonProperty("Vcurs")
    var curs: Double? = null

    @JsonProperty("Vcode")
    var code: String? = null

    @JsonProperty("VchCode")
    var chCode: String? = null

    @JsonProperty("VunitRate")
    var unitRate: Double? = null

}
