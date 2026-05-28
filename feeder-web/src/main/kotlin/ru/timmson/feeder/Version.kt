package ru.timmson.feeder

import org.springframework.stereotype.Service

@Service
data class Version(
    val number: String = "101",
    val feature: String = "Fix cache error"
) {

    override fun toString(): String {
        return "$number // $feature"
    }
}

