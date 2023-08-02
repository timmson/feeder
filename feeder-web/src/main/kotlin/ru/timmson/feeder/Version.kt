package ru.timmson.feeder

import org.springframework.stereotype.Service

@Service
data class Version(
    val number: String = "20",
    val feature: String = "Improve CV register 2"
) {

    override fun toString(): String {
        return "$number // $feature"
    }
}

