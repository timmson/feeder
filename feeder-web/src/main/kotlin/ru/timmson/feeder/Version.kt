package ru.timmson.feeder

import org.springframework.stereotype.Service

@Service
data class Version(
    val number: String = "37",
    val feature: String = "Migrate to JavaTelegramBotApi 7.1.1"
) {

    override fun toString(): String {
        return "$number // $feature"
    }
}

