package ru.timmson.feeder.cv

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.Arguments.*
import org.junit.jupiter.params.provider.MethodSource
import org.mockito.junit.jupiter.MockitoExtension
import java.util.stream.Stream


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

    @ParameterizedTest
    @MethodSource("data")
    fun parseFileNameWithUnderscores(arrange: String, expected: String) {
        val actualCV = cvRegistrar.parseFileName(arrange)

        assertEquals(expected, actualCV)
    }

    companion object {
        @JvmStatic
        fun data(): Stream<Arguments> = Stream.of(
            of("Иванов Иван SDET.docx", "Иванов"),
            of("Разработчик_Mobile_Сидоров_Алексей.docx", "Сидоров"),
            of("_NET_бэкенд_разработчик_Бурковский_Антон_м.docx", "Бурковский"),
            of("Системный_аналитик_Шишкин_Василий.docx", "Шишкин"),
            of("Системный_аналитик_Сидоров_Иван.docx", "Сидоров"),
            of("Android_разработчик_Сидоров_Иван", "Сидоров")
        )
    }


}
