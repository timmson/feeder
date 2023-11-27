package ru.timmson.feeder.cv

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.Arguments.of
import org.junit.jupiter.params.provider.MethodSource
import org.mockito.junit.jupiter.MockitoExtension
import ru.timmson.feeder.common.FeederConfig
import ru.timmson.feeder.cv.model.CVRegisterRequest
import java.util.stream.Stream


@ExtendWith(MockitoExtension::class)
class CVRegistrarShould {

    private lateinit var cvRegistrar: CVRegistrar

    private val feederConfig = FeederConfig()

    @BeforeEach
    fun setUp() {
        feederConfig.cvChannelUrl = "https://some.com"
        feederConfig.cvInnerChannelId = 333L
        feederConfig.cvInnerChannelRegion = "Европа"
        cvRegistrar = CVRegistrar(feederConfig)
    }

    @Test
    fun parseInnerCV() {
        val expectedCV = CV().apply {
            url = "${feederConfig.cvChannelUrl}/${feederConfig.cvInnerChannelId}/2"
            name = "Сидорова"
            area = feederConfig.cvInnerChannelRegion
            title = "SDET"
            type = "Внутренний"
        }
        val caption = "SDET (сторонний)\n" +
                "Ключевые навыки:\n" +
                "• Java  \n" +
                "• Git  \n" +
                "• Автоматизированное тестирование\n" +
                "#sdet"
        val request = CVRegisterRequest(forwardedChatId = -1000000000333L, forwardedMessageId = 2, caption = caption, fileName = "Сидорова Лилия SDET.docx")

        val actualCV = cvRegistrar.parse(request)

        assertEquals(expectedCV, actualCV)
    }

    @Test
    fun parseOuterCV() {
        val expectedCV = CV().apply {
            url = "${feederConfig.cvChannelUrl}/1/2"
            name = "Иванов"
            area = "Омск"
            title = "SDET"
            type = "Внешний"
        }
        val caption = "SDET (сторонний)\n" +
                "Омск\n" +
                "Ключевые навыки:\n" +
                "• Java  \n" +
                "• Git  \n" +
                "• Автоматизированное тестирование\n" +
                "#sdet"
        val request = CVRegisterRequest(forwardedChatId = 1L, forwardedMessageId = 2, caption = caption, fileName = "Иванов Иван SDET.docx")

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
            of("CV_Иванов SDET.docx", "Иванов"),
            of("Иванов Иван SDET.docx", "Иванов"),
            of("Разработчик_Mobile_Сидоров_Алексей.docx", "Сидоров"),
            of("_NET_бэкенд_разработчик_Бурковский_Антон_м.docx", "Бурковский"),
            of("Системный_аналитик_Шишкин_Василий.docx", "Шишкин"),
            of("Системный_аналитик_Сидоров_Иван.docx", "Сидоров"),
            of("Android_разработчик_Сидоров_Иван", "Сидоров")
        )
    }


}
