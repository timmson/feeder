package ru.timmson.feeder.lingua.translate

import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.springframework.stereotype.Service
import ru.timmson.feeder.common.FeederConfig
import ru.timmson.feeder.common.logger
import ru.timmson.feeder.lingua.oxford.OxfordDictionaryClient

@Service
class LinguaLeoClient(
    private val feederConfig: FeederConfig
) {
    private val log = logger<OxfordDictionaryClient>()

    private val contentTypeJson: MediaType by lazy {
        "application/json; charset=utf-8".toMediaType()
    }

    private val httpClient: OkHttpClient by lazy {
        OkHttpClient()
    }


    private fun createRequest(url: String, body: String): Request =
        body.toRequestBody(contentTypeJson).let {
            Request.Builder().url(url).post(it).build()
        }

    fun fetch(request: String): String {
        val url = "${feederConfig.linguaLeoUrl}getTranslates"

        log.info("Fetching ($url) ...")

        log.fine(request)
        val response = createRequest(url, request).let {
            httpClient.newCall(it).execute().let { response ->
                log.fine(response.toString())
                response.body?.string() ?: ""
            }
        }

        log.info("Fetching (...) = [${response.length}]")

        return response
    }

}
