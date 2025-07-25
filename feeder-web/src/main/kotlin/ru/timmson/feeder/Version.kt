package ru.timmson.feeder

import org.springframework.stereotype.Service

@Service
data class Version(
    val number: String = "67",
    val feature: String = "Remove foreign indexes"
) {

    override fun toString(): String {
        return "$number // $feature"
    }
}

