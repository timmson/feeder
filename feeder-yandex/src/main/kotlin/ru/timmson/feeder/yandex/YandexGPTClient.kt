package ru.timmson.feeder.yandex

import com.fasterxml.jackson.databind.ObjectMapper
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.springframework.stereotype.Service
import ru.timmson.feeder.common.logger
import ru.timmson.feeder.yandex.model.YandexCloudTokenRequest
import ru.timmson.feeder.yandex.model.YandexCloudTokenResponse
import ru.timmson.feeder.yandex.model.YandexGPTRequest
import ru.timmson.feeder.yandex.model.YandexGPTResponse
import java.io.IOException


@Service
class YandexGPTClient(
    private val objectMapper: ObjectMapper
) {

    private val log = logger<YandexGPTClient>()
    private val client: OkHttpClient = OkHttpClient()

    @Throws(IOException::class)
    fun createIAMToken(request: YandexCloudTokenRequest): YandexCloudTokenResponse {
        val body = objectMapper.writeValueAsString(request)
        val request = createJsonRequest("https://iam.api.cloud.yandex.net/iam/v1/tokens", body).build()

        val response = client.newCall(request).execute()

        val responseBody = response.body?.string()
        if (response.code != 200) {
            log.info { "Request body: $body" }
            log.info { "Response body: $responseBody" }
        }

        return objectMapper.readValue(responseBody, YandexCloudTokenResponse::class.java)
    }

    @Throws(IOException::class)
    fun completion(yandexGPTRequest: YandexGPTRequest): YandexGPTResponse {
        val body = objectMapper.writeValueAsString(yandexGPTRequest)

        val request = createJsonRequest("https://llm.api.cloud.yandex.net/foundationModels/v1/completion", body)
            .header("Authorization", "Bearer " + yandexGPTRequest.token).build()

        val response = client.newCall(request).execute()

        val responseBody = response.body?.string()
        if (response.code != 200) {
            log.info { "Request body: $body" }
            log.info { "Response body: $responseBody" }
        }

        return objectMapper.readValue(responseBody, YandexGPTResponse::class.java)
    }

    private fun createJsonRequest(uri: String, body: String): Request.Builder =
        Request.Builder()
            .url(uri)
            .post(body.toRequestBody("application/json".toMediaTypeOrNull()))

}


