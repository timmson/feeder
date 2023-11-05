package ru.timmson.feeder

import org.springframework.stereotype.Service

@Service
data class Version(
    val number: String = "24",
    val feature: String = "Improve CV registration 6"
) {

    override fun toString(): String {
        return "$number // $feature"
    }
}

