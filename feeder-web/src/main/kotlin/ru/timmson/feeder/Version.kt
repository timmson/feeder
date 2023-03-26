package ru.timmson.feeder

import org.springframework.stereotype.Service

@Service
data class Version(
    val number: String = "15",
    val feature: String = "Add oxford dictionary"
) {

    override fun toString(): String {
        return "$number // $feature"
    }
}

