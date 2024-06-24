package ru.timmson.feeder

import org.springframework.stereotype.Service

@Service
data class Version(
    val number: String = "45",
    val feature: String = "Refactor caching"
) {

    override fun toString(): String {
        return "$number // $feature"
    }
}

