package ru.timmson.feeder.stock.dao

import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import ru.timmson.feeder.stock.FeederStockBeanConfig
import java.net.http.HttpConnectTimeoutException
import java.util.concurrent.TimeUnit

class RequesterImplShould {

    private lateinit var requesterImpl: RequesterImpl

    private lateinit var webServer: MockWebServer

    @BeforeEach
    fun setUp() {
        requesterImpl = RequesterImpl(FeederStockBeanConfig().getHttpClient(1))
        webServer = MockWebServer()
    }

    @Test
    fun fetchSuccessfulResponse() {
        val expected = ""
        webServer.enqueue(MockResponse().setBody(expected))
        val url = webServer.url("/").toString()

        val actual = requesterImpl.fetch(url)
        assertEquals(expected, actual)
    }

    @Test
    fun handleTimeout() {
        webServer.enqueue(MockResponse().setBodyDelay(2, TimeUnit.MILLISECONDS))
        val url = webServer.url("/").toString()

        assertThrows<HttpConnectTimeoutException> { requesterImpl.fetch(url) }
    }
}