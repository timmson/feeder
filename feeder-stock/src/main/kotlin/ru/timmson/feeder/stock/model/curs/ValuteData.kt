package ru.timmson.feeder.stock.model.curs

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper

class ValuteData {

    @JsonProperty("OnDate")
    var onDate: String? = null

    @JsonProperty("ValuteCursOnDate")
    @JacksonXmlElementWrapper(useWrapping = false)
    var valuteCursOnDate: List<ValuteCursOnDate>? = null
}

