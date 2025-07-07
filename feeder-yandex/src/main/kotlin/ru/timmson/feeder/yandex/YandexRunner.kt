package ru.timmson.feeder.yandex

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import ru.timmson.feeder.yandex.model.YandexGPTMessage
import ru.timmson.feeder.yandex.model.YandexGPTRequest


/**
 * https://yandex.cloud/ru/docs/iam/concepts/authorization/oauth-token
 */
const val oAuthToken = "yyy"

fun main() {

    val objectMapper = ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    val yandexGPTClient = YandexGPTClient(objectMapper)

    val iamToken = yandexGPTClient.createIAMToken(oAuthToken)
    println("IAM Token: $iamToken")

    //val iamToken = "xxxxxxx"

    val yandexGPTRequest = YandexGPTRequest(
        token = iamToken,
        modelUri = "gpt://b1ger5agr13geapg3rda/yandexgpt-lite",
        messages = listOf(
            YandexGPTMessage().apply { role = "user"; text = "How are you?" }
        )
    )

    val yandexGPTResponse = yandexGPTClient.completion(yandexGPTRequest)

    print(yandexGPTResponse.result.alternatives.first().message.text)

}