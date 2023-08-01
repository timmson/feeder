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
import ru.timmson.feeder.cv.model.Fields
import ru.timmson.feeder.cv.model.Record
import ru.timmson.feeder.cv.model.TableRequest

@Service
class CVRegistrar(
    private val feederConfig: FeederConfig
) {

    private val log = logger<CVRegistrar>()

    private val contentTypeJson: MediaType by lazy {
        "application/json; charset=utf-8".toMediaType()
    }

    private val httpClient: OkHttpClient by lazy {
        OkHttpClient()
    }

    private fun createRequest(url: String, token: String, body: String): Request =
        body.toRequestBody(contentTypeJson).let {
            Request.Builder().url(url).header("Authorization", "Bearer $token").post(it).build()
        }

    fun parse(request: CVRegisterRequest): CV {

        val cv = request.caption.lines().let {
            CV().apply {
                name = request.fileName.split(" ").first()
                area = it[1]
                title = it.last().drop(1).uppercase()
            }
        }

        val tr = TableRequest(
            records = listOf(
                Record(
                    Fields(
                        Name = cv.name,
                        Area = cv.area,
                        Title = cv.title,
                        Type = cv.type,
                        Date = "new"
                    )
                )
            )
        )

        val rawRequest = ObjectMapper().writeValueAsString(tr)

        val response = createRequest(feederConfig.airtableUrl, feederConfig.airtableToken, rawRequest).let {
            httpClient.newCall(it).execute().let { response ->
                log.info(response.toString())
                response.body?.string() ?: ""
            }
        }

        return cv
    }

}
