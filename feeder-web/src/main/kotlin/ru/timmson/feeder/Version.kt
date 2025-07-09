package ru.timmson.feeder

import org.springframework.stereotype.Service

@Service
data class Version(
    val number: String = "65",
    val feature: String = "Fix token"
) {

    override fun toString(): String {
        return "$number // $feature"
    }
}

