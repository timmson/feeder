package ru.timmson.feeder.stock.dao

import org.springframework.stereotype.Service
import ru.timmson.feeder.common.FeederConfig
import ru.timmson.feeder.common.logger
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpRequest.BodyPublishers.ofString
import java.net.http.HttpResponse
import java.util.concurrent.TimeUnit

@Service
class Requester(
    private val feederConfig: FeederConfig
) {

    private val log = logger<Requester>()
    private lateinit var httpClient: HttpClient

    fun get(url: String): String {
        log.info("Entering get($url) ... ")

        val request = HttpRequest.newBuilder(URI(url)).GET().build()

        val responseBody = getHttpClient()
            .sendAsync(request, HttpResponse.BodyHandlers.ofString())
            .get(feederConfig.timeoutInMillis, TimeUnit.MILLISECONDS)
            .body()

        log.info("Leaving get(.../${url.split("/").last()}) = [length=${responseBody.length}] ")
        return responseBody
    }

    fun postSOAP(url: String, action: String, requestBody: String): String {
        log.info("Entering postSOAP($url, $action, [body]) ... ")
        log.fine("Body [$requestBody]")

        val request = HttpRequest.newBuilder(URI(url)).POST(ofString(requestBody))
            .header("Content-Type", "text/xml;charset=UTF-8")
            .header("SOAPAction", action)
            .build()

        val responseBody = getHttpClient()
            .sendAsync(request, HttpResponse.BodyHandlers.ofString())
            .get(feederConfig.timeoutInMillis, TimeUnit.MILLISECONDS)
            .body()

        log.info("Leaving postSOAP(.../${url.split("/").last()}) = [length=${responseBody.length}] ")
        return responseBody
    }

    private fun getHttpClient(): HttpClient {
        if (!::httpClient.isInitialized) {
            httpClient = HttpClient.newHttpClient()
        }
        return httpClient
    }
}
