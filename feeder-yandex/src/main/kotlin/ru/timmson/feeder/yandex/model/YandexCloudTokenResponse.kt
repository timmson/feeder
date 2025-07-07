package ru.timmson.feeder.yandex.model

import java.time.LocalDateTime

class YandexCloudTokenResponse() {
    var iamToken: String = ""
    var expiresAt: LocalDateTime? = null
}
