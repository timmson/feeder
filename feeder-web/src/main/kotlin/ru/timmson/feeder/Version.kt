package ru.timmson.feeder

import org.springframework.stereotype.Service

@Service
data class Version(
    val number: String = "38",
    val feature: String = "Improve stocks"
) {

    override fun toString(): String {
        return "$number // $feature"
    }
}

