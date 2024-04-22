package ru.timmson.feeder

import org.springframework.stereotype.Service

@Service
data class Version(
    val number: String = "40",
    val feature: String = "Add cache for foreign stocks"
) {

    override fun toString(): String {
        return "$number // $feature"
    }
}

