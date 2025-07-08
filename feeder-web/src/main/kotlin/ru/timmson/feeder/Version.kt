package ru.timmson.feeder

import org.springframework.stereotype.Service

@Service
data class Version(
    val number: String = "64",
    val feature: String = "Fix timeout"
) {

    override fun toString(): String {
        return "$number // $feature"
    }
}

