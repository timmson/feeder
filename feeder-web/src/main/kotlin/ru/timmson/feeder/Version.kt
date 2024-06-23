package ru.timmson.feeder

import org.springframework.stereotype.Service

@Service
data class Version(
    val number: String = "44",
    val feature: String = "Fix put error"
) {

    override fun toString(): String {
        return "$number // $feature"
    }
}

