package ru.timmson.feeder.yandex.model

import com.fasterxml.jackson.annotation.JsonIgnore

class YandexGPTRequest(
    @JsonIgnore val token: String,
    val modelUri: String,
    val messages: List<YandexGPTMessage>,
    val completionOptions: YandexGPTCompletionsOptions = YandexGPTCompletionsOptions(),
)
