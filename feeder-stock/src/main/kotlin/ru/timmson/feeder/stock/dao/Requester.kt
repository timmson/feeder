package ru.timmson.feeder.stock.dao

import org.springframework.stereotype.Service
import ru.timmson.feeder.common.FeederConfig
import ru.timmson.feeder.common.logger
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.util.concurrent.TimeUnit

@Service
class Requester(
    private val feederConfig: FeederConfig
) {

    private val log = logger<Requester>()

    fun fetch(url: String): String {
        log.info("Entering fetch ($url) ... ")

        val httpClient = HttpClient.newHttpClient()
        val request = HttpRequest.newBuilder(URI(url)).GET().build()
        val completableFuture = httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
        val body = completableFuture.get(feederConfig.timeoutInMillis, TimeUnit.MILLISECONDS).body()

        log.info("Leaving fetch (.../${url.split("/").last()}) = [length=${body.length}] ")
        return body
    }
}
