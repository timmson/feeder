package ru.timmson.feeder.stock.model.curs

import com.fasterxml.jackson.annotation.JsonProperty

class GetCursOnDateXMLResponse {

    @JsonProperty("GetCursOnDateXMLResult")
    var getCursOnDateXMLResult: GetCursOnDateXMLResult? = null
}
