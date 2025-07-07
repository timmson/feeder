package ru.timmson.feeder.service

import org.springframework.stereotype.Service
import ru.timmson.feeder.common.FeederConfig
import ru.timmson.feeder.yandex.PDF2String
import ru.timmson.feeder.yandex.YandexGPTClient
import ru.timmson.feeder.yandex.model.YandexGPTMessage
import ru.timmson.feeder.yandex.model.YandexGPTRequest

@Service
class CVEstimationService(
    private val feederConfig: FeederConfig,
    private val pdf2String: PDF2String,
    private val yandexGPTClient: YandexGPTClient
) {

    private val prompt = "Оцени уровень разработчика в процентах. 0% - вообще нет опытп в программировании, 100% - инженер мирового уровня. Ответь только числом процентов."

    fun estimate(type: String, cvFile: ByteArray): String {
        val cv = pdf2String.convert(cvFile)
        val iamToken = yandexGPTClient.createIAMToken(feederConfig.yandexToken)

        val yandexGPTRequest = YandexGPTRequest(
            token = iamToken,
            modelUri = feederConfig.yandexModel,
            messages = listOf(
                YandexGPTMessage().apply { role = "user"; text = prompt },
                YandexGPTMessage().apply { role = "user"; text = cv }
            )
        )

        val result = yandexGPTClient.completion(yandexGPTRequest)

        return result.result.alternatives.first().message.text
    }

}