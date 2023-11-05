package ru.timmson.feeder.service

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import ru.timmson.feeder.cv.CV

class PrintServiceShould {


    @Test
    fun printAsRow() {
        val expected = "=SPLIT(\"Иванов,Омск,SDET,Внешний,25.12.2012\"; \",\")"

        val actual = PrintService().printCV(CV().apply {
            name = "Иванов"
            area = "Омск"
            title = "SDET"
            type = "Внешний"
        }, "25.12.2012")

        Assertions.assertEquals(expected, actual)
    }

}
