package ru.timmson.feeder

import org.springframework.stereotype.Service

@Service
data class Version(
    val number: String = "35",
    val feature: String = "Add inflation and key rates"
) {

    override fun toString(): String {
        return "$number // $feature"
    }
}

