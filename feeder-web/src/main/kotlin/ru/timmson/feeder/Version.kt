package ru.timmson.feeder

import org.springframework.stereotype.Service

@Service
data class Version(
    val number: String = "17",
    val feature: String = "Upgrade spring"
) {

    override fun toString(): String {
        return "$number // $feature"
    }
}

