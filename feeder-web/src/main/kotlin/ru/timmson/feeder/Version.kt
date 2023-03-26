package ru.timmson.feeder

import org.springframework.stereotype.Service

@Service
data class Version(
    val number: String = "16",
    val feature: String = "Add translation dictionary"
) {

    override fun toString(): String {
        return "$number // $feature"
    }
}

