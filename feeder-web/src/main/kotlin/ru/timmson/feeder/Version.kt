package ru.timmson.feeder

import org.springframework.stereotype.Service

@Service
data class Version(
    val number: String = "25",
    val feature: String = "Improve caching and scheduling"
) {

    override fun toString(): String {
        return "$number // $feature"
    }
}

