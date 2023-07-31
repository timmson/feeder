package ru.timmson.feeder

import org.springframework.stereotype.Service

@Service
data class Version(
    val number: String = "18",
    val feature: String = "Add CV register"
) {

    override fun toString(): String {
        return "$number // $feature"
    }
}

