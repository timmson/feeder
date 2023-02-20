package ru.timmson.feeder.stock.dao

import org.springframework.stereotype.Service
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.util.logging.Logger

@Service
class RequesterImpl(private val httpClient: HttpClient) : Requester {

    private val log = Logger.getLogger(RequesterImpl::class.java.toString())

    override fun fetch(url: String): String {
        log.info("Entering fetch ($url) ... ")

        val request = HttpRequest.newBuilder(URI(url)).GET().build()
        val body = httpClient.send(request, HttpResponse.BodyHandlers.ofString()).body()

        log.info("Leaving fetch (.../${url.split("/").last()}) = [length=${body.length}] ")
        return body
    }
}