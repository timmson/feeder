package ru.timmson.feeder.lingua.oxford

import okhttp3.OkHttpClient
import okhttp3.Request
import org.springframework.stereotype.Service
import ru.timmson.feeder.common.logger

@Service
class OxfordDictionaryClient {
    private val log = logger<OxfordDictionaryClient>()

    private val httpClient: OkHttpClient by lazy { OkHttpClient() }

    private fun createRequest(url: String): Request {
        return Request.Builder().url(url).build()
    }

    fun fetch(url: String): String {
        log.info("Fetching ($url) ...")

        val response = createRequest(url).let {
            httpClient.newCall(it).execute().let { response ->
                response.body?.string() ?: ""
            }
        }

        log.info("Fetching (...) = [${response.length}]")

        return response
    }

}
