package ru.timmson.feeder.lingua.oxford

import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import ru.timmson.feeder.test.HttpMethod
import ru.timmson.feeder.test.StubServer

class OxfordDictionaryClientShould {

    @Test
    fun fetch() {
        val word = "some word"
        val url = "${webServer.url}definition/english/$word"
        val expected = "some body"

        webServer.setResponse {
            it.apply {
                code = 200
                contentType = "text/html"
                body = expected
            }
        }
        val actual = oxfordDictionaryClient.fetch(url)

        webServer.checkRequest {
            assertEquals(HttpMethod.GET, it.method)
            assertEquals("/definition/english/${word.replace(" ", "%20")}", it.path)
        }
        assertEquals(expected, actual)
    }

    companion object {

        private lateinit var oxfordDictionaryClient: OxfordDictionaryClient

        private lateinit var webServer: StubServer

        @BeforeAll
        @JvmStatic
        fun setUp() {
            webServer = StubServer()
            oxfordDictionaryClient = OxfordDictionaryClient()
        }

        @AfterAll
        @JvmStatic
        fun tearDown() {
            webServer.stop()
        }
    }
}
