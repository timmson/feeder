package ru.timmson.feeder.common

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import java.time.LocalDate

class DateTest {

    @Test
    fun getToday() {
        assertNotNull(Date.today)
    }

    @Test
    fun format() {
        assertEquals("10.01.2023", Date.format(LocalDate.of(2023, 1, 10)))
    }
}
