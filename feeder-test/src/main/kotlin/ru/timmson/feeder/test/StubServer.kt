package ru.timmson.feeder.test

import okhttp3.mockwebserver.MockWebServer

class StubServer {

    private val webServer: MockWebServer = MockWebServer()

    val url: String
        get() = webServer.url(".").toString()

    fun checkRequest(assertion: (request: StubRequest) -> Unit) = assertion.invoke(StubRequest(webServer.takeRequest()))

    fun setResponse(fill: (response: StubResponse) -> StubResponse) = webServer.enqueue(fill.invoke(StubResponse()).mockResponse)

    fun stop() = webServer.shutdown()

}
