package ru.timmson.feeder.calendar

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.LocalDate

class ProdCalShould {

    private lateinit var prodCal: ProdCal

    @BeforeEach
    fun setUp() {
        prodCal = ProdCal()
    }

    @Test
    fun knowThat26And29OfMayIsWorking() {
        val expectedWorkingDays = listOf(2, 3, 4, 5, 10, 11, 12, 15, 16, 17, 18, 19, 22, 23, 24, 25, 26, 29, 30, 31)

        val actualWorkingDays = (1..31).filter { prodCal.isWorking(LocalDate.of(2023, 5, it)) }

        assertEquals(expectedWorkingDays, actualWorkingDays)
    }

    @Test
    fun knowThat22OfFebIsWorking() {
        assertTrue(prodCal.isWorking(LocalDate.of(2023, 2, 22)))
    }

    @Test
    fun knowThat23OfFebIsNotWorking() {
        assertFalse(prodCal.isWorking(LocalDate.of(2023, 2, 23)))
    }

    @Test
    fun knowThat25OfFebIsNotWorking() {
        assertFalse(prodCal.isWorking(LocalDate.of(2023, 2, 25)))
    }

}
