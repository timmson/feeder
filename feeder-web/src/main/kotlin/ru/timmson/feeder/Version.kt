package ru.timmson.feeder

import org.springframework.stereotype.Service

@Service
data class Version(
    val number: String = "70",
    val feature: String = "Upgrade versions"
) {

    override fun toString(): String {
        return "$number // $feature"
    }
}

