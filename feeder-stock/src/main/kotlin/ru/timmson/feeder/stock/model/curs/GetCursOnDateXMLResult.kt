package ru.timmson.feeder.stock.model.curs

import com.fasterxml.jackson.annotation.JsonProperty

class GetCursOnDateXMLResult {
    @JsonProperty("ValuteData")
    var valuteData: ValuteData? = null
}
