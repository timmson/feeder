package ru.timmson.feeder.test

enum class HttpMethod(private val method: String) {
    EMPTY(""),
    GET("GET"),
    POST("POST");

    override fun toString() = method

}
