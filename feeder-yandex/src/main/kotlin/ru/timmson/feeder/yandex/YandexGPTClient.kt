package ru.timmson.feeder.yandex

import com.fasterxml.jackson.databind.ObjectMapper
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import org.springframework.stereotype.Service
import ru.timmson.feeder.common.logger
import ru.timmson.feeder.yandex.model.YandexCloudTokenRequest
import ru.timmson.feeder.yandex.model.YandexCloudTokenResponse
import ru.timmson.feeder.yandex.model.YandexGPTRequest
import ru.timmson.feeder.yandex.model.YandexGPTResponse
import java.io.IOException
import java.util.concurrent.TimeUnit


@Service
class YandexGPTClient(
    private val objectMapper: ObjectMapper
) {

    private val log = logger<YandexGPTClient>()

    private var client: OkHttpClient? = null

    @Throws(IOException::class)
    fun createIAMToken(request: YandexCloudTokenRequest): YandexCloudTokenResponse {
        log.info { "Entering createIAMToken()" }
        val body = objectMapper.writeValueAsString(request)
        val request = createJsonRequest("https://iam.api.cloud.yandex.net/iam/v1/tokens", body).build()

        val response = call(request)

        val responseBody = response.body?.string()
        if (response.code != 200) {
            log.info { "Request body: $body" }
            log.info { "Response body: $responseBody" }
        }

        val tokenResponse = objectMapper.readValue(responseBody, YandexCloudTokenResponse::class.java)

        log.info { "Leaving createIAMToken(...) = token(${tokenResponse.expiresAt})" }
        return tokenResponse
    }

    @Throws(IOException::class)
    fun completion(yandexGPTRequest: YandexGPTRequest): YandexGPTResponse {
        log.info { "Entering completion(...)" }

        val body = objectMapper.writeValueAsString(yandexGPTRequest)

        val request = createJsonRequest("https://llm.api.cloud.yandex.net/foundationModels/v1/completion", body)
            .header("Authorization", "Bearer " + yandexGPTRequest.token).build()

        val response = call(request)

        val responseBody = response.body?.string()
        if (response.code != 200) {
            log.info { "Request body: $body" }
            log.info { "Response body: $responseBody" }
        }

        val gptResponse = objectMapper.readValue(responseBody, YandexGPTResponse::class.java)

        log.info { "Leaving completion(...)" }
        return gptResponse
    }

    private fun call(request: Request): Response {
        if (client == null) {
            client = OkHttpClient.Builder().readTimeout(120, TimeUnit.SECONDS).build()
        }
        return client!!.newCall(request).execute()
    }

    private fun createJsonRequest(uri: String, body: String): Request.Builder =
        Request.Builder()
            .url(uri)
            .post(body.toRequestBody("application/json".toMediaTypeOrNull()))

}


