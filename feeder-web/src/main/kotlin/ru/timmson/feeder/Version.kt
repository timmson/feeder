package ru.timmson.feeder

import org.springframework.stereotype.Service

@Service
data class Version(
    val number: String = "50",
    val feature: String = "Remove airtable"
) {

    override fun toString(): String {
        return "$number // $feature"
    }
}

