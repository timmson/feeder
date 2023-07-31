package ru.timmson.feeder.common

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import java.time.LocalDate
import java.time.ZoneId

class DateShould {

    @Test
    fun getToday() {
        assertNotNull(Date.today)
    }

    @Test
    fun formatLocalDate() {
        assertEquals("10.01.2023", Date.format(LocalDate.of(2023, 1, 10)))
    }

    @Test
    fun formatTimestamp() {
        println(ZoneId.systemDefault())
        assertEquals("28.07.2023", Date.format(1690554059L))
    }
}
