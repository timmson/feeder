package ru.timmson.feeder.stock.dao

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.util.concurrent.TimeUnit
import java.util.logging.Logger

@Service
class RequesterImpl(
    @Value("\${feeder.stock.timeout}") private val timeoutInMillis: Long
) : Requester {

    private val log = Logger.getLogger(RequesterImpl::class.java.toString())

    override fun fetch(url: String): String {
        log.info("Entering fetch ($url) ... ")

        val httpClient = HttpClient.newHttpClient()
        val request = HttpRequest.newBuilder(URI(url)).GET().build()
        val completableFuture = httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
        val body = completableFuture.get(timeoutInMillis, TimeUnit.MILLISECONDS).body()

        log.info("Leaving fetch (.../${url.split("/").last()}) = [length=${body.length}] ")
        return body
    }
}