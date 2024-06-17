package ru.timmson.feeder.stock.model

import com.fasterxml.jackson.annotation.JsonProperty

class Envelope {
    @JsonProperty("Body")
    var body: Body? = null
}

