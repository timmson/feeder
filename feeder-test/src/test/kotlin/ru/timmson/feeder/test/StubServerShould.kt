package ru.timmson.feeder.test

import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test

class StubServerShould {

    @Test
    fun respondOk() {
        val expectedMediaType = "plain/text; charset=utf-8".toMediaType()

        val expectedResponseCode = 200
        val expectedResponseBody = "some response body"

        val expectedRequestPath = "some/path/to"
        val expectedRequestBody = "some request body"
        val requestBody = expectedRequestBody.toRequestBody(expectedMediaType)

        val request = Request.Builder().url("${url}${expectedRequestPath}").post(requestBody).build()

        webServer.setResponse {
            it.apply {
                code = expectedResponseCode
                contentType = expectedMediaType.toString()
                body = expectedResponseBody
            }
        }

        val actual = httpClient.newCall(request).execute()

        webServer.checkRequest {
            assertEquals(expectedMediaType, it.mediaType)
            assertEquals(HttpMethod.POST, it.method)
            assertEquals("/${expectedRequestPath}", it.path)
            assertEquals(expectedRequestBody, it.body)
        }
        assertEquals(expectedResponseCode, actual.code)
        assertEquals(expectedMediaType.toString(), actual.headers["content-type"])
        assertEquals(expectedResponseBody, actual.body?.string())
    }


    companion object {

        private lateinit var webServer: StubServer

        private lateinit var url: String

        private lateinit var httpClient: OkHttpClient

        @BeforeAll
        @JvmStatic
        fun setUp() {
            webServer = StubServer()
            url = webServer.url
            httpClient = OkHttpClient()
        }

        @AfterAll
        @JvmStatic
        fun tearDown() {
            webServer.stop()
        }


    }

}
