package ru.timmson.feeder.cv

import com.fasterxml.jackson.databind.ObjectMapper
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.springframework.stereotype.Service
import ru.timmson.feeder.common.FeederConfig
import ru.timmson.feeder.common.logger
import ru.timmson.feeder.cv.model.Record
import ru.timmson.feeder.cv.model.TableRequest

@Service
class AirtableAPIClient(
    private val feederConfig: FeederConfig,
    private val objectMapper: ObjectMapper
) {

    private val log = logger<AirtableAPIClient>()

    private val contentTypeJson: MediaType by lazy {
        "application/json; charset=utf-8".toMediaType()
    }

    private val httpClient: OkHttpClient by lazy {
        OkHttpClient()
    }

    fun add(record: Record): Int {
        log.info("Entering Airtable add($record) ...")
        val body = objectMapper.writeValueAsString(TableRequest(listOf(record)))

        val request = body.toRequestBody(contentTypeJson).let {
            Request
                .Builder()
                .url(feederConfig.airtableUrl)
                .header("Authorization", "Bearer ${feederConfig.airtableToken}")
                .post(it)
                .build()
        }

        val response = httpClient.newCall(request).execute()

        if (response.code == 200) {
            log.info("Leaving add(...) = ${response.code}")
        } else {
            log.info("Leaving add(...) = [${response.code},${response.body.toString()}]")
        }

        return response.code
    }
}
