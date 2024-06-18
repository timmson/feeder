package ru.timmson.feeder

import org.springframework.stereotype.Service

@Service
data class Version(
    val number: String = "43",
    val feature: String = "Refactor fetching"
) {

    override fun toString(): String {
        return "$number // $feature"
    }
}

