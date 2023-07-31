package ru.timmson.feeder.common

import java.time.LocalDate
import java.time.format.DateTimeFormatter

object Date {

    val today: String
        get() = format(LocalDate.now())

    fun format(date: LocalDate) = date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))

}
