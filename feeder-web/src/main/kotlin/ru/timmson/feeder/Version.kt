package ru.timmson.feeder

import org.springframework.stereotype.Service

@Service
data class Version(
    val number: String = "41",
    val feature: String = "Add multiple users"
) {

    override fun toString(): String {
        return "$number // $feature"
    }
}

