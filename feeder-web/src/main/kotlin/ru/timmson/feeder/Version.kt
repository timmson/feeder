package ru.timmson.feeder

import org.springframework.stereotype.Service

@Service
data class Version(
    val number: String = "42",
    val feature: String = "Change currency rates"
) {

    override fun toString(): String {
        return "$number // $feature"
    }
}

