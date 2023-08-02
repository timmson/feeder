package ru.timmson.feeder

import org.springframework.stereotype.Service

@Service
data class Version(
    val number: String = "21",
    val feature: String = "Improve CV register 3"
) {

    override fun toString(): String {
        return "$number // $feature"
    }
}

