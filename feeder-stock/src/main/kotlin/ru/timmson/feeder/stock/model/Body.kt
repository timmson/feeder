package ru.timmson.feeder.stock.model

import com.fasterxml.jackson.annotation.JsonProperty
import ru.timmson.feeder.stock.model.curs.GetCursOnDateXMLResponse
import ru.timmson.feeder.stock.model.main.MainInfoXMLResponse

class Body {
    @JsonProperty("MainInfoXMLResponse")
    var mainInfoXMLResponse: MainInfoXMLResponse? = null

    @JsonProperty("GetCursOnDateXMLResponse")
    var getCursOnDateXMLResponse: GetCursOnDateXMLResponse? = null
}
