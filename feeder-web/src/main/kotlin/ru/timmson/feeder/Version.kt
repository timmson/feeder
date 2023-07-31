package ru.timmson.feeder

import org.springframework.stereotype.Service

@Service
data class Version(
    val number: String = "19",
    val feature: String = "Improve CV register"
) {

    override fun toString(): String {
        return "$number // $feature"
    }
}

