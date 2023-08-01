package ru.timmson.feeder.cv.model

data class Fields(
    val Name: String,
    val Area: String,
    val Title: String,
    val Type: String,
    val Date: String,
    val Verified: Boolean = false
)
