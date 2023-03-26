package ru.timmson.feeder.lingua.oxford

import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import ru.timmson.feeder.common.FeederConfig
import ru.timmson.feeder.test.HttpMethod
import ru.timmson.feeder.test.StubServer

class OxfordDictionaryClientShould {

    @Test
    fun fetchData() {
        val word = "some word"
        val expected = "some body"

        webServer.setResponse {
            it.apply {
                code = 200
                contentType = "text/html"
                body = expected
            }
        }
        val actual = oxfordDictionaryClientShould.fetch(word)

        webServer.checkRequest {
            assertEquals(HttpMethod.GET, it.method)
            assertEquals("/definition/english/${word.replace(" ", "%20")}", it.path)
        }
        assertEquals(expected, actual)
    }

    companion object {

        private lateinit var oxfordDictionaryClientShould: OxfordDictionaryClient
        private lateinit var webServer: StubServer
        private lateinit var feederConfig: FeederConfig

        @BeforeAll
        @JvmStatic
        fun setUp() {
            webServer = StubServer()
            feederConfig = FeederConfig().apply {
                linguaURL = webServer.url
            }
            oxfordDictionaryClientShould = OxfordDictionaryClient(feederConfig)
        }

        @AfterAll
        @JvmStatic
        fun tearDown() {
            webServer.stop()
        }
    }
}
