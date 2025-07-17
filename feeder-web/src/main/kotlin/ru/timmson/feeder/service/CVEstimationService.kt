package ru.timmson.feeder.service

import org.springframework.stereotype.Service
import ru.timmson.feeder.common.FeederConfig
import ru.timmson.feeder.yandex.JobRequirementService
import ru.timmson.feeder.yandex.PDF2String
import ru.timmson.feeder.yandex.YandexGPTClient
import ru.timmson.feeder.yandex.model.YandexCloudTokenRequest
import ru.timmson.feeder.yandex.model.YandexCloudTokenResponse
import ru.timmson.feeder.yandex.model.YandexGPTMessage
import ru.timmson.feeder.yandex.model.YandexGPTRequest
import java.time.LocalDateTime

@Service
class CVEstimationService(
    private val pdf2String: PDF2String,
    private val feederConfig: FeederConfig,
    private val yandexGPTClient: YandexGPTClient,
    private val jobRequirementService: JobRequirementService
) {

    private var token: YandexCloudTokenResponse? = null

    private val systemPrompt = "Ты — опытный технический рекрутер из IT-компании.\n" +
            "Проверь соответствие резюме по каждому требованию из секции основных и дополнительных.\n " +
            "Проверяй требования СТРОГО по порядку как они приходят на вход. Дай следующие ответы.\n" +
            "1. Какой процент основных требований подтвержден по данным из резюме?\n" +
            "1.1 Перечисли обязательные требования, по которым не удалось найти информацию в резюме?\n" +
            "2. Какой процент дополнительных требований подтвержден по данным из резюме?\n"

    fun estimate(type: String, cvFile: ByteArray): String {
        val requirements = jobRequirementService.read()

        if (!requirements.containsKey(type))
            return "Не могу оценить \"$type\" - нет описания"

        val iamToken = getIamToken()

        if (iamToken == null)
            return "Токен не получен"

        val cv = pdf2String.convert(cvFile)

        val yandexGPTRequest = YandexGPTRequest(
            token = iamToken,
            modelUri = feederConfig.yandexModel,
            messages = listOf(
                YandexGPTMessage().apply { role = "system"; text = systemPrompt },
                YandexGPTMessage().apply { role = "user"; text = requirements[type].toString() },
                YandexGPTMessage().apply { role = "user"; text = "Резюме:\n\n$cv" }
            )
        )

        val result = yandexGPTClient.completion(yandexGPTRequest)

        return result.result.alternatives.first().message.text
    }

    private fun getIamToken(): String? {
        if (token == null || token?.expiresAt?.isBefore(LocalDateTime.now()) ?: false) {
            token = yandexGPTClient.createIAMToken(YandexCloudTokenRequest(feederConfig.yandexToken))
        }
        return token?.iamToken
    }

}