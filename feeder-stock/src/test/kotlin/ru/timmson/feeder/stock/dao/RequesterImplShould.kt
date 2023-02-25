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

class RequesterImplShould {

    private lateinit var requesterImpl: RequesterImpl

    private lateinit var webServer: MockWebServer

    private lateinit var url: String

    @BeforeEach
    fun setUp() {
        val config = FeederConfig().apply { timeoutInMillis = 500 }
        requesterImpl = RequesterImpl(config)
        webServer = MockWebServer()
        url = webServer.url("/").toString()
    }

    @Test
    fun fetchSuccessfulResponse() {
        val expected = "x"
        webServer.enqueue(MockResponse().setBody(expected))

        val actual = requesterImpl.fetch(url)

        assertEquals(expected, actual)
    }

    @Test
    fun handleTimeout() {
        webServer.enqueue(MockResponse().setBodyDelay(600, TimeUnit.MILLISECONDS).setBody("-"))

        assertThrows<TimeoutException> { requesterImpl.fetch(url) }
    }

    @AfterEach
    fun tearDown() {
        webServer.shutdown()
    }
}