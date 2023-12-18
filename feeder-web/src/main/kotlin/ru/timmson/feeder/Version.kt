package ru.timmson.feeder

import org.springframework.stereotype.Service

@Service
data class Version(
    val number: String = "27",
    val feature: String = "Add developer_ pattern"
) {

    override fun toString(): String {
        return "$number // $feature"
    }
}

