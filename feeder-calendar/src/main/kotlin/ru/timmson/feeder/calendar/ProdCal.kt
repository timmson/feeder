package ru.timmson.feeder.calendar

import java.time.LocalDate

interface ProdCal {

    fun isWorking(date: LocalDate): Boolean

}