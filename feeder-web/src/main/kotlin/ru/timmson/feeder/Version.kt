package ru.timmson.feeder

import org.springframework.stereotype.Service

@Service
data class Version(
    val number: String = "14",
    val feature: String = "Extract version"
) {

    override fun toString(): String {
        return "$number // $feature"
    }
}

