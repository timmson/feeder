package ru.timmson.feeder.stock.dao

import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import ru.timmson.feeder.common.FeederConfig
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

class RequesterShould {

    private lateinit var requester: Requester

    private lateinit var webServer: MockWebServer

    private lateinit var url: String

    @BeforeEach
    fun setUp() {
        val config = FeederConfig().apply { timeoutInMillis = 500 }
        requester = Requester(config)
        webServer = MockWebServer()
        url = webServer.url("/").toString()
    }

    @Test
    fun getSuccessfully() {
        val expected = "x"
        webServer.enqueue(MockResponse().setBody(expected))

        val actual = requester.postSOAP(url, "action", "body")

        assertEquals(expected, actual)
    }

    @Test
    fun postSOAPSuccessfully() {
        val expected = "x"
        webServer.enqueue(MockResponse().setBody(expected))

        val actual = requester.get(url)

        assertEquals(expected, actual)
    }

    @Test
    fun handleTimeout() {
        webServer.enqueue(MockResponse().setBodyDelay(600, TimeUnit.MILLISECONDS).setBody("-"))

        assertThrows<TimeoutException> { requester.get(url) }
    }

    @AfterEach
    fun tearDown() {
        webServer.shutdown()
    }
}
