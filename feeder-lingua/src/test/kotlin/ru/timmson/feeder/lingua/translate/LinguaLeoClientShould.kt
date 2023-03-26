package ru.timmson.feeder.lingua.translate

import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import ru.timmson.feeder.common.FeederConfig
import ru.timmson.feeder.test.HttpMethod
import ru.timmson.feeder.test.StubServer

class LinguaLeoClientShould {

    @Test
    fun fetch() {
        val request = "{\"text\":\"spoil\"}"
        val expected = "{\"some\": \"body\"}"

        webServer.setResponse {
            it.apply {
                code = 200
                contentType = "application/json"
                body = expected
            }
        }
        val actual = linguaLeoClient.fetch(request)

        webServer.checkRequest {
            assertEquals(HttpMethod.POST, it.method)
            assertEquals("/getTranslates", it.path)
            assertEquals(request, it.body)
        }
        assertEquals(expected, actual)
    }

    companion object {

        private lateinit var linguaLeoClient: LinguaLeoClient
        private lateinit var webServer: StubServer
        private lateinit var feederConfig: FeederConfig

        @BeforeAll
        @JvmStatic
        fun setUp() {
            webServer = StubServer()
            feederConfig = FeederConfig().apply {
                linguaLeoUrl = webServer.url
            }
            linguaLeoClient = LinguaLeoClient(feederConfig)
        }

        @AfterAll
        @JvmStatic
        fun tearDown() {
            webServer.stop()
        }
    }
}
