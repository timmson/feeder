package ru.timmson.feeder

import org.springframework.stereotype.Service

@Service
data class Version(
    val number: String = "68",
    val feature: String = "Add date and channel name"
) {

    override fun toString(): String {
        return "$number // $feature"
    }
}

