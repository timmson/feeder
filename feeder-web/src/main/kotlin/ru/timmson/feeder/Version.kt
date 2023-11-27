package ru.timmson.feeder

import org.springframework.stereotype.Service

@Service
data class Version(
    val number: String = "26",
    val feature: String = "Add CV_ pattern"
) {

    override fun toString(): String {
        return "$number // $feature"
    }
}

