package ru.timmson.feeder

import org.springframework.stereotype.Service

@Service
data class Version(
    val number: String = "31",
    val feature: String = "Add insertion into sheets directly 2"
) {

    override fun toString(): String {
        return "$number // $feature"
    }
}

