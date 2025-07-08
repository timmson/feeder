package ru.timmson.feeder

import org.springframework.stereotype.Service

@Service
data class Version(
    val number: String = "62",
    val feature: String = "Add company"
) {

    override fun toString(): String {
        return "$number // $feature"
    }
}

