package ru.timmson.feeder.cv

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.junit.jupiter.MockitoExtension


@ExtendWith(MockitoExtension::class)
class CVRegistrarShould {

    private lateinit var cvRegistrar: CVRegistrar

    @BeforeEach
    fun setUp() {
        cvRegistrar = CVRegistrar()
    }

    @Test
    fun save() {
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

        val actualCV = cvRegistrar.parse(request)

        assertEquals(expectedCV, actualCV)
    }
}
