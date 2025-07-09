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
            "Проверь соответствие резюме по каждому требованию. Используй строго эти правила:\n" +
            "1. Проверяй требования СТРОГО по порядку как они приходят на вход. " +
            "1.1. На одно требование дай СТРОГО ОДИН ответ в формате: \"название требования: + или - (комментарий)\"\n" +
            "1.2. Перед ответом проверь, что количество ответов СТРОГО ровняется количеству требований\n" +
            "2. Если требование ЯВНО указано в резюме то поставь +\n" +
            "2.1. Некоторые требования могут иметь перечисления, например: Знания kafka, hazelcast\n" +
            "Если явно найдено хоть одно из перечислений в требованиях (например hazelcast), ставь +\n" +
            "3. Если требование отсутствует то поставь –\n" +
            "4. ОТДЕЛЬНО дай комментарий (что найдено или чего не хватает СТРОГО НЕ БОЛЕЕ 7 слов)\n" +
            "5. В конце сообщения напиши, какой процент какой процент обязательных требования выполнен.\n"

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