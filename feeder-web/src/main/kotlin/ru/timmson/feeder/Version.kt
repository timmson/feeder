package ru.timmson.feeder

import org.springframework.stereotype.Service

@Service
data class Version(
    val number: String = "100",
    val feature: String = "Switch to web"
) {

    override fun toString(): String {
        return "$number // $feature"
    }
}

