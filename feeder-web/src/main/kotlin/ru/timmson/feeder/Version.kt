package ru.timmson.feeder

import org.springframework.stereotype.Service

@Service
data class Version(
    val number: String = "30",
    val feature: String = "Add insertion into sheets directly"
) {

    override fun toString(): String {
        return "$number // $feature"
    }
}

