package ru.timmson.feeder.lingua.oxford

import okhttp3.OkHttpClient
import okhttp3.Request
import org.springframework.stereotype.Service
import ru.timmson.feeder.common.FeederConfig
import ru.timmson.feeder.common.logger

@Service
class OxfordDictionaryClient(
    private val feederConfig: FeederConfig
) {
    private val log = logger<OxfordDictionaryClient>()

    private val httpClient: OkHttpClient by lazy { OkHttpClient() }

    private fun createRequest(word: String) =
        Request.Builder().url("${feederConfig.linguaURL}definition/english/$word").build()

    fun fetch(word: String): String {
        log.info("Fetching ([$word]) ...")

        val response = createRequest(word).let {
            httpClient.newCall(it).execute().let { response ->
                response.body?.string() ?: ""
            }
        }

        log.info("Fetching (...) = [${response.length}]")

        return response
    }

}
