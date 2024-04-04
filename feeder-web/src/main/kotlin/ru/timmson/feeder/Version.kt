package ru.timmson.feeder

import org.springframework.stereotype.Service

@Service
data class Version(
    val number: String = "36",
    val feature: String = "Add some fixes"
) {

    override fun toString(): String {
        return "$number // $feature"
    }
}

