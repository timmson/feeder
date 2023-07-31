package ru.timmson.feeder.common

import java.time.Instant.ofEpochSecond
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter

object Date {

    val today: String
        get() = format(LocalDate.now())

    fun format(date: LocalDate) =
        date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))

    fun format(timestamp: Long) =
        ofEpochSecond(timestamp)
            .atZone(ZoneId.of("Europe/Moscow"))
            .toLocalDate().let { format(it) }

}
