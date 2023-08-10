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

        val actualCV = cvRegistrar.parse(request)

        assertEquals(expectedCV, actualCV)
    }

    @Test
    fun parseFileNameWithSpaces() {
        val expected = "Иванов"

        val actualCV = cvRegistrar.parseFileName("Иванов Иван SDET.docx")

        assertEquals(expected, actualCV)
    }

    @Test
    fun parseFileNameWithUnderscores1() {
        val expected = "Сидоров"

        val actualCV = cvRegistrar.parseFileName("Разработчик_Mobile_Сидоров_Алексей.docx")

        assertEquals(expected, actualCV)
    }

    @Test
    fun parseFileNameWithUnderscores2() {
        val expected = "Бурковский"

        val actualCV = cvRegistrar.parseFileName("_NET_бэкенд_разработчик_Бурковский_Антон_м.docx")

        assertEquals(expected, actualCV)
    }

    @Test
    fun parseFileNameWithUnderscores3() {
        val expected = "Шишкин"

        val actualCV = cvRegistrar.parseFileName("Системный_аналитик_Шишкин_Василий.docx")

        assertEquals(expected, actualCV)
    }
}
