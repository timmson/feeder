package ru.timmson.feeder.cv

import com.fasterxml.jackson.databind.ObjectMapper
import okhttp3.MediaType.Companion.toMediaType
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import ru.timmson.feeder.common.FeederConfig
import ru.timmson.feeder.cv.model.Fields
import ru.timmson.feeder.cv.model.Record
import ru.timmson.feeder.cv.model.TableRequest
import ru.timmson.feeder.test.HttpMethod
import ru.timmson.feeder.test.StubServer

class AirtableAPIClientShould {

    @Test
    fun addSuccessfully() {
        val expected = 200
        val record =
            Record(
                Fields(
                    name = "name",
                    area = "area",
                    title = "title",
                    type = "type",
                    date = "date",
                )
            )

        webServer.setResponse {
            it.apply {
                code = expected
                contentType = "application/json; charset=utf-8"
                body = "{}"
            }
        }
        val actual = airtableAPIClient.add(record)

        webServer.checkRequest {
            assertEquals("application/json; charset=utf-8".toMediaType(), it.mediaType)
            assertEquals(HttpMethod.POST, it.method)
            assertEquals(objectMapper.writeValueAsString(TableRequest(listOf(record))), it.body)
        }

        assertEquals(expected, actual)
    }

    @Test
    fun addFailure() {
        val expected = 422
        val record =
            Record(
                Fields(
                    name = "name",
                    area = "area",
                    title = "title",
                    type = "type",
                    date = "date",
                )
            )

        webServer.setResponse {
            it.apply {
                code = expected
                contentType = "application/json; charset=utf-8"
                body = "{}"
            }
        }
        val actual = airtableAPIClient.add(record)

        webServer.checkRequest {
            assertEquals("application/json; charset=utf-8".toMediaType(), it.mediaType)
            assertEquals(HttpMethod.POST, it.method)
            assertEquals(objectMapper.writeValueAsString(TableRequest(listOf(record))), it.body)
        }

        assertEquals(expected, actual)
    }

    companion object {

        private lateinit var airtableAPIClient: AirtableAPIClient

        private lateinit var webServer: StubServer

        private val objectMapper = ObjectMapper()

        @BeforeAll
        @JvmStatic
        fun setUp() {
            webServer = StubServer()
            val feederConfig = FeederConfig().apply {
                airtableUrl = webServer.url
                airtableToken = "x"
            }
            airtableAPIClient = AirtableAPIClient(feederConfig, objectMapper)
        }

        @AfterAll
        @JvmStatic
        fun tearDown() {
            webServer.stop()
        }
    }
}
