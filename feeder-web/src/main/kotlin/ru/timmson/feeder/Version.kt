package ru.timmson.feeder

import org.springframework.stereotype.Service

@Service
data class Version(
    val number: String = "46",
    val feature: String = "Refactor caching 2"
) {

    override fun toString(): String {
        return "$number // $feature"
    }
}

