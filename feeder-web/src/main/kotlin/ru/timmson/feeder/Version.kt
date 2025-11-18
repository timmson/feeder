package ru.timmson.feeder

import org.springframework.stereotype.Service

@Service
data class Version(
    val number: String = "69",
    val feature: String = "Adjust time for requests"
) {

    override fun toString(): String {
        return "$number // $feature"
    }
}

