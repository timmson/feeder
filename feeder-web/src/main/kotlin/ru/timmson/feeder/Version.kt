package ru.timmson.feeder

import org.springframework.stereotype.Service

@Service
data class Version(
    val number: String = "66",
    val feature: String = "Fix prompt"
) {

    override fun toString(): String {
        return "$number // $feature"
    }
}

