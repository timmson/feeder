package ru.timmson.feeder.calendar

import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class ProdCal {

    private val exceptions = mapOf(
        LocalDate.of(2023, 1, 1) to false,
        LocalDate.of(2023, 1, 2) to false,
        LocalDate.of(2023, 1, 3) to false,
        LocalDate.of(2023, 1, 4) to false,
        LocalDate.of(2023, 1, 5) to false,
        LocalDate.of(2023, 1, 6) to false,
        LocalDate.of(2023, 2, 23) to false,
        LocalDate.of(2023, 2, 24) to false,
        LocalDate.of(2023, 3, 8) to false,
        LocalDate.of(2023, 5, 1) to false,
        LocalDate.of(2023, 5, 8) to false,
        LocalDate.of(2023, 5, 9) to false,
        LocalDate.of(2023, 6, 12) to false,
        LocalDate.of(2023, 11, 6) to false
    )

    fun isWorking(date: LocalDate): Boolean =
        exceptions.getOrDefault(date, date.dayOfWeek.value < 6)

}
