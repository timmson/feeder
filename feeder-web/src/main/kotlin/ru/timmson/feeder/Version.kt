package ru.timmson.feeder

import org.springframework.stereotype.Service

@Service
data class Version(
    val number: String = "28",
    val feature: String = "Add - delimiter"
) {

    override fun toString(): String {
        return "$number // $feature"
    }
}

