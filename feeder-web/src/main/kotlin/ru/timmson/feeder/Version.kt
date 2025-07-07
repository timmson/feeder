package ru.timmson.feeder

import org.springframework.stereotype.Service

@Service
data class Version(
    val number: String = "60",
    val feature: String = "Add GPT"
) {

    override fun toString(): String {
        return "$number // $feature"
    }
}

