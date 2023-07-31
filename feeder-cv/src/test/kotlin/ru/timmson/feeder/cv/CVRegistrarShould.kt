package ru.timmson.feeder.cv

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class CVRegistrarShould {

    @Test
    fun parse() {
        val expectedCV = CV().apply {
            name = "Иванов"
            area = "Омск"
            title = "SDET"
        }
        val caption = "SDET (сторонний)\n" +
                "Омск\n" +
                "Ключевые навыки:\n" +
                "• Java  \n" +
                "• Git  \n" +
                "• Автоматизированное тестирование\n" +
                "#sdet"
        val request = CVRegisterRequest(caption = caption, fileName = "Иванов Иван SDET.docx")

        val actualCV = CVRegistrar().parse(request)

        assertEquals(expectedCV, actualCV)
    }
}
