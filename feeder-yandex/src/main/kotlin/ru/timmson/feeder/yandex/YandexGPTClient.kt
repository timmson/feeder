package ru.timmson.feeder.yandex

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.stereotype.Service
import ru.timmson.feeder.common.logger
import ru.timmson.feeder.yandex.model.YandexGPTRequest
import ru.timmson.feeder.yandex.model.YandexGPTResponse
import java.io.IOException
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.net.http.HttpResponse.BodyHandlers.ofString


@Service
class YandexGPTClient(
    private val objectMapper: ObjectMapper
) {

    private val log = logger<YandexGPTClient>()
    private val client: HttpClient = HttpClient.newHttpClient()

    @Throws(IOException::class)
    fun createIAMToken(token: String): String {
        val request = createJsonRequest("https://iam.api.cloud.yandex.net/iam/v1/tokens", "{\"yandexPassportOauthToken\":\"$token\"}").build()

        val response: HttpResponse<String?> = client.send(request, ofString())

        log.info { "Response headers: " + response.headers() }
        //log.info { "Response Status Code: " + response.statusCode() }
        log.info { "Response Body: " + response.body() }

        return ""
    }

    @Throws(IOException::class)
    fun completion(yandexGPTRequest: YandexGPTRequest): YandexGPTResponse {
        val body = objectMapper.writeValueAsString(yandexGPTRequest)

        val request = createJsonRequest("https://llm.api.cloud.yandex.net/foundationModels/v1/completion", body)
            .header("Authorization", "Bearer " + yandexGPTRequest.token).build()

        val response: HttpResponse<String?> = client.send(request, ofString())

        log.info { "Response Status Code: " + response.statusCode() }

        if (response.statusCode() != 200) {
            log.info { "Request body: $body" }
            log.info { "Response body: " + response.body() }
        }

        return objectMapper.readValue(response.body(), YandexGPTResponse::class.java)
    }

    private fun createJsonRequest(uri: String, body: String): HttpRequest.Builder =
        HttpRequest.newBuilder()
            .uri(URI.create(uri))
            .header("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(body))

}


